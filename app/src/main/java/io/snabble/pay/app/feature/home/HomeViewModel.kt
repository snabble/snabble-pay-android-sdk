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
import io.snabble.pay.app.domain.session.usecase.GetCurrentSessionUseCase
import io.snabble.pay.app.domain.session.usecase.UpdateTokenUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccounts: GetAllAccountCardsUseCase,
    private val addAccountUseCase: AddAccountUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
    private val loadSessionFor: GetCurrentSessionUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _validationLink = MutableSharedFlow<String>()
    val validationLink = _validationLink.asSharedFlow()

    private val _error = MutableSharedFlow<ErrorResponse?>()
    val error = _error.asSharedFlow()

    private var sessionRefreshJob: Job? = null

    private fun refresh() {
        viewModelScope.launch {
            getAccounts()
                .collect { result ->
                    result
                        .onError { _error.emit(it) }
                        .onSuccess { accountList ->
                            if (accountList.isEmpty()) {
                                _uiState.tryEmit(AddNewCart)
                            } else {
                                _uiState.tryEmit(ShowAccounts(accountList))
                            }
                        }
                }
        }
    }

    fun setActiveAccountCard(accountId: String) {
        viewModelScope.launch {
            loadSessionFor(accountId)
                .onError { _error.emit(it) }
                .onSuccess { updateAccountsAndRefreshTimer(accountId, it) }
        }
    }

    private fun updateAccountsAndRefreshTimer(accountId: String, session: SessionModel) {
        addSessionTokenToAccount(accountId, session.token)
        setTokenRefreshTimer(accountId, session)
    }

    private fun setTokenRefreshTimer(accountId: String, session: SessionModel) {
        sessionRefreshJob?.cancel()
        sessionRefreshJob = viewModelScope.launch {
            val refreshTokenJob = viewModelScope.async {
                delay(refreshDelay(session.token.refreshAt))
                updateTokenUseCase(session.id)
            }

            delay(refreshDelay(session.token.validUntil))
            if (!refreshTokenJob.isCompleted) {
                removeExpiredSessionToken(accountId)
            }

            refreshTokenJob.await()
                .onError { _error.emit(it) }
                .onSuccess {
                    updateAccountsAndRefreshTimer(
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
            when (val state = uiState.value) {
                is ShowAccounts -> {
                    val accounts = state.accountCards.map { accMod ->
                        if (accMod.accountId == accountId) {
                            accMod.copy(sessionToken = sessionToken)
                        } else {
                            accMod
                        }
                    }
                    delay(1000)
                    _uiState.tryEmit(ShowAccounts(accounts))
                }
                else -> {}
            }
        }
    }

    fun getValidationLink() {
        viewModelScope.launch {
            val result = addAccountUseCase(
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
        sessionRefreshJob?.cancel()
    }
}

sealed interface UiState

object Loading : UiState

object AddNewCart : UiState

data class ShowAccounts(
    val accountCards: List<AccountCard>,
) : UiState
