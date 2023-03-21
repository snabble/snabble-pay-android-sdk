package io.snabble.pay.app.feature.detailsaccount

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.DeleteAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.core.PayError
import io.snabble.pay.mandate.domain.model.Mandate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsAccountViewModel @Inject constructor(
    private val getCard: GetAccountCardUseCase,
    private val setCardLabel: SetAccountCardLabelUseCase,
    private val getMandate: GetMandateUseCase,
    private val createMandateUseCase: CreateMandateUseCase,
    private val acceptPendingMandate: AcceptMandateUseCase,
    private val removeAccount: DeleteAccountUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _error = MutableSharedFlow<PayError?>()
    val error = _error.asSharedFlow()

    val accountId: String = requireNotNull(savedStateHandle["accountId"])

    init {
        getAccount(accountId)
    }

    fun acceptMandate() {
        viewModelScope.launch {
            val result = getMandate(accountId = accountId)
            when (result) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> {
                    val mandate = result.value
                    if (mandate != null) {
                        acceptMandate(accountId = accountId, mandate.id)
                    } else {
                        _error.tryEmit(null)
                    }
                }
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            val result = removeAccount(accountId)
            when (result) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> _uiState.tryEmit(AccountDeleted(result.value))
            }
        }
    }

    private suspend fun getMandateFor(accountId: String): Mandate? {
        val result = getMandate(accountId)
        when (result) {
            is AppError -> {
                _error.emit(result.value)
                return null
            }
            is AppSuccess -> return result.value
        }
    }

    private fun getAccount(accountId: String) {
        viewModelScope.launch {
            val result = getCard(accountId)
            when (result) {
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
        val result = createMandateUseCase(accountId)
        when (result) {
            is AppError -> _error.emit(result.value)
            is AppSuccess -> return
        }
    }

    private suspend fun acceptMandate(accountId: String, mandateId: String) {
        val result = acceptPendingMandate(accountId = accountId, mandateId = mandateId)
        when (result) {
            is AppError -> _error.emit(result.value)
            is AppSuccess -> {
                val accountCardResult = getCard(accountId)
                when (accountCardResult) {
                    is AppError -> _error.emit(accountCardResult.value)
                    is AppSuccess -> {
                        val mandate = getMandateFor(accountId)
                        _uiState.tryEmit(ShowAccount(accountCardResult.value, mandate))
                    }
                }
            }
        }
    }

    fun updateAccountName(name: String, colors: List<String>) {
        viewModelScope.launch {
            setCardLabel(accountId = accountId, name = name, colors = colors)
            // TODO: What do we need to do here?
            // _uiState.tryEmit(ShowAccount(accountManager.getAccountModel(id)))
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
