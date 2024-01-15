package io.snabble.pay.app.feature.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.data.utils.onError
import io.snabble.pay.app.data.utils.onSuccess
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.AddAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAllAccountCardsUseCase
import io.snabble.pay.app.domain.session.SessionModel
import io.snabble.pay.app.domain.session.SessionTokenModel
import io.snabble.pay.app.domain.session.usecase.CreateSessionUseCase
import io.snabble.pay.app.domain.session.usecase.GetCurrentSessionUseCase
import io.snabble.pay.app.domain.session.usecase.UpdateTokenUseCase
import io.snabble.pay.app.domain.transactions.GetAllPurchasesUseCase
import io.snabble.pay.app.domain.transactions.PurchasesModel
import io.snabble.pay.core.Reason
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
@Suppress("TooManyFunctions")
class HomeViewModel @Inject constructor(
    private val getAccounts: GetAllAccountCardsUseCase,
    private val getValidationLink: AddAccountUseCase,
    private val createNewSession: CreateSessionUseCase,
    private val fetchNewToken: UpdateTokenUseCase,
    private val loadSessionFor: GetCurrentSessionUseCase,
    private val getAllPurchasesUseCase: GetAllPurchasesUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _validationLink = MutableSharedFlow<String>()
    val validationLink = _validationLink.asSharedFlow()

    private val _error = MutableSharedFlow<ErrorResponse?>()
    val error = _error.asSharedFlow()

    private var tokenRefreshJob: Job? = null
    private var sessionRefreshJob: Job? = null

    init {
        loadPreviousPurchases()
    }

    private fun loadPreviousPurchases() {
        viewModelScope.launch {
            getAllPurchasesUseCase().collectLatest { purchases ->
                _uiState.update { it.copy(purchases = purchases) }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            getAccounts()
                .collect { result ->
                    result
                        .onError { _error.emit(it) }
                        .onSuccess { accountList ->
                            if (accountList.isEmpty()) {
                                _uiState.update { it.copy(isLoading = false, isNewUser = true) }
                            } else {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        accountCards = accountList
                                    )
                                }
                            }
                        }
                }
        }
    }

    fun setActiveAccountCard(accountId: String) {
        viewModelScope.launch {
            loadSessionFor(accountId)
                .onError { _error.emit(it) }
                .onSuccess { updateAccountsAndStartAutoRefresh(accountId, it) }
        }
    }

    private fun updateAccountsAndStartAutoRefresh(accountId: String, session: SessionModel) {
        addSessionTokenToAccount(accountId, session.token)
        setTokenRefreshTimer(accountId, session)
        setSessionRefreshTimer(accountId, session)
    }

    private fun setSessionRefreshTimer(accountId: String, session: SessionModel) {
        sessionRefreshJob?.cancel()
        sessionRefreshJob = viewModelScope.launch {
            delay(refreshDelay(session.expiresAt))
            createNewSession(accountId)
                .onError { _error.emit(it) }
                .onSuccess {
                    updateAccountsAndStartAutoRefresh(
                        accountId = accountId,
                        session = it
                    )
                }
        }
    }

    private fun setTokenRefreshTimer(accountId: String, session: SessionModel) {
        tokenRefreshJob?.cancel()
        tokenRefreshJob = viewModelScope.launch {
            val refreshTokenJob = viewModelScope.async {
                delay(refreshDelay(session.token.refreshAt))
                fetchNewToken(session.id)
            }

            delay(refreshDelay(session.token.expiresAt))
            if (!refreshTokenJob.isCompleted) {
                removeExpiredSessionToken(accountId)
            }

            refreshTokenJob.await()
                .onError {
                    if (it?.reason == Reason.INVALID_SESSION_STATE) {
                        createNewSession(accountId).onError { err ->
                            _error.emit(err)
                        }.onSuccess { session ->
                            updateAccountsAndStartAutoRefresh(
                                accountId = accountId,
                                session = session
                            )
                        }
                    } else {
                        _error.emit(it)
                    }
                }
                .onSuccess {
                    updateAccountsAndStartAutoRefresh(
                        accountId = accountId,
                        session = session.copy(token = it)
                    )
                }
        }
    }

    private fun refreshDelay(zonedDateTime: ZonedDateTime): Long {
        return Duration.between(ZonedDateTime.now(), zonedDateTime).toMillis()
    }

    private fun removeExpiredSessionToken(accountId: String) {
        addSessionTokenToAccount(accountId, null)
    }

    private fun addSessionTokenToAccount(
        accountId: String,
        sessionToken: SessionTokenModel?,
    ) {
        viewModelScope.launch {
            val accountCards = uiState.value.accountCards
            if (accountCards != null) {
                val accounts = accountCards.map { accMod ->
                    if (accMod.accountId == accountId) {
                        accMod.copy(sessionToken = sessionToken)
                    } else {
                        accMod
                    }
                }
                delay(1.seconds)
                _uiState.update { it.copy(accountCards = accounts) }
            }

        }
    }

    fun getValidationLink() {
        viewModelScope.launch {
            val result = getValidationLink(
                appUri = "snabble-pay://account/check",
                city = "Berlin",
                twoLetterIsoCountryCode = "DE"
            )
            when (result) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> _validationLink.emit(result.value.validationLink)
            }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        refresh()
    }

    override fun onPause(owner: LifecycleOwner) {
        tokenRefreshJob?.cancel()
        sessionRefreshJob?.cancel()
    }
}

data class UiState(
    val isLoading: Boolean = false,
    val isNewUser: Boolean = false,
    val accountCards: List<AccountCard>? = null,
    val purchases: List<PurchasesModel>? = null,
)
