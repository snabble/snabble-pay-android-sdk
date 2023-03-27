package io.snabble.pay.app.feature.detailsaccount

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.DeleteAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.app.domain.session.usecase.GetCurrentSessionUseCase
import io.snabble.pay.mandate.domain.model.Mandate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsAccountViewModel @Inject constructor(
    private val getCard: GetAccountCardUseCase,
    private val setCardLabel: SetAccountCardLabelUseCase,
    private val getMandate: GetMandateUseCase,
    private val createMandateUseCase: CreateMandateUseCase,
    private val removeAccount: DeleteAccountUseCase,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _error = MutableSharedFlow<ErrorResponse?>()
    val error = _error.asSharedFlow()

    val accountId: String = requireNotNull(savedStateHandle["accountId"])

    init {
        getAccount(accountId)
    }

    fun deleteAccount() {
        viewModelScope.launch {
            when (val result = removeAccount(accountId)) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> _uiState.tryEmit(AccountDeleted(result.value))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O) fun getSessionToken(accountId: String) {
        viewModelScope.launch {
            when (val state = uiState.value) {
                is ShowAccount -> {
                    when (val sessionResult = getCurrentSessionUseCase(accountId)) {
                        is AppError -> {
                            _error.emit(sessionResult.value)
                        }
                        is AppSuccess -> {
                            _uiState.tryEmit(
                                ShowAccount(
                                    state.accountCard.copy(session = sessionResult.value),
                                    mandate = state.mandate
                                )
                            )
                        }
                    }
                }
                else -> {}
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

    private fun getAccount(accountId: String) {
        viewModelScope.launch {
            when (val result = getCard(accountId)) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> {
                    val account = result.value
                    if (account.mandateState == MandateState.MISSING) {
                        createMandate(accountId)
                    }
                    val mandate = getMandateFor(accountId)

                    updateAccountName(account.name, account.cardBackgroundColor)

                    _uiState.tryEmit(ShowAccount(account, mandate))
                }
            }
        }
    }

    private suspend fun createMandate(accountId: String) {
        when (val result = createMandateUseCase(accountId)) {
            is AppError -> _error.emit(result.value)
            is AppSuccess -> return
        }
    }

    fun updateAccountName(name: String, colors: List<String>) {
        viewModelScope.launch {
            setCardLabel(accountId = accountId, name = name, colors = colors)
        }
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
