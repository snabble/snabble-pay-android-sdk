package io.snabble.pay.app.feature.detailsaccount

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.data.utils.onError
import io.snabble.pay.app.data.utils.onSuccess
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.DeleteAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.app.domain.session.SessionModel
import io.snabble.pay.app.domain.session.SessionTokenModel
import io.snabble.pay.app.domain.session.usecase.GetCurrentSessionUseCase
import io.snabble.pay.app.domain.session.usecase.UpdateTokenUseCase
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.shared.account.domain.model.Account
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
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
@Suppress("TooManyFunctions")
class DetailsAccountViewModel @Inject constructor(
    private val getAccountCard: GetAccountCardUseCase,
    private val setCardLabelAndColors: SetAccountCardLabelUseCase,
    private val getMandate: GetMandateUseCase,
    private val createMandate: CreateMandateUseCase,
    private val removeAccount: DeleteAccountUseCase,
    private val loadSessionFor: GetCurrentSessionUseCase,
    private val fetchNewToken: UpdateTokenUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), DefaultLifecycleObserver {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _error = MutableSharedFlow<ErrorResponse?>()
    val error = _error.asSharedFlow()

    val accountId: String = requireNotNull(savedStateHandle["accountId"])
    private var sessionRefreshJob: Job? = null

    private fun getAccount(accountId: String) {
        viewModelScope.launch {
            getAccountCard(accountId)
                .onError { _error.emit(it) }
                .onSuccess { account ->
                    if (account.mandateState == Account.MandateState.MISSING) {
                        createMandateFor(accountId)
                    }
                    val mandate = getMandateFor(accountId)

                    _uiState.tryEmit(ShowAccount(account, mandate))
                    loadSessionToken()
                }
        }
    }

    private suspend fun getMandateFor(accountId: String): Mandate? {
        return when (val result = getMandate(accountId)) {
            is AppError -> {
                _error.emit(result.value)
                null
            }

            is AppSuccess -> result.value
        }
    }

    private suspend fun createMandateFor(accountId: String) {
        when (val result = createMandate(accountId)) {
            is AppError -> _error.emit(result.value)
            is AppSuccess -> return
        }
    }

    private suspend fun loadSessionToken() {
        loadSessionFor(accountId)
            .onError { _error.emit(it) }
            .onSuccess {
                updateAccountsAndRefreshTimer(it)
            }
    }

    private suspend fun updateAccountsAndRefreshTimer(session: SessionModel) {
        addSessionTokenToAccount(session.token)
        setTokenRefreshTimer(session)
    }

    private fun setTokenRefreshTimer(session: SessionModel) {
        sessionRefreshJob?.cancel()
        sessionRefreshJob = viewModelScope.launch {
            val refreshTokenJob = viewModelScope.async {
                delay(refreshDelay(session.token.refreshAt))
                fetchNewToken(session.id)
            }

            delay(refreshDelay(session.token.expiresAt))
            if (!refreshTokenJob.isCompleted) {
                removeExpiredSessionToken()
            }

            refreshTokenJob.await()
                .onError { _error.emit(it) }
                .onSuccess {
                    updateAccountsAndRefreshTimer(session = session.copy(token = it))
                }
        }
    }

    private fun refreshDelay(zonedDateTime: ZonedDateTime): Long =
        Duration.between(ZonedDateTime.now(), zonedDateTime).toMillis()

    private suspend fun removeExpiredSessionToken() = addSessionTokenToAccount(null)

    private suspend fun addSessionTokenToAccount(
        sessionToken: SessionTokenModel?,
    ) {
        when (val state = uiState.value) {
            is ShowAccount -> {
                val account = state.accountCard.copy(sessionToken = sessionToken)
                delay(1.seconds)
                _uiState.tryEmit(ShowAccount(account, state.mandate))
            }

            else -> {}
        }
    }

    fun updateAccountName(name: String, colors: List<String>) {
        viewModelScope.launch {
            setCardLabelAndColors(accountId = accountId, name = name, colors = colors)
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            removeAccount(accountId = accountId)
                .onError { _error.emit(it) }
                .onSuccess { _uiState.tryEmit(AccountDeleted(it)) }
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        getAccount(accountId)
    }

    override fun onPause(owner: LifecycleOwner) {
        sessionRefreshJob?.cancel()
    }
}

sealed interface UiState

object Loading : UiState

data class ShowAccount(
    val accountCard: AccountCard,
    val mandate: Mandate?,
) : UiState

data class AccountDeleted(
    val accountCard: AccountCard,
) : UiState
