package io.snabble.pay.app.feature.detailsaccount

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.DeleteAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
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
    private val acceptPendingMandate: AcceptMandateUseCase,
    private val removeAccount: DeleteAccountUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _error = MutableSharedFlow<String?>()
    val error = _error.asSharedFlow()

    val accountId: String = requireNotNull(savedStateHandle["accountId"])

    init {
        getAccount(accountId)
    }

    fun acceptMandate() {
        viewModelScope.launch {
            getMandate(accountId = accountId)
                .onFailure {
                    _error.tryEmit(it.message)
                }
                .onSuccess {
                    if (it != null) {
                        acceptMandate(accountId = accountId, it.id)
                    } else {
                        _error.tryEmit("No Mandate exists")
                    }
                }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            val accountCard = removeAccount(accountId)
            _uiState.tryEmit(AccountDeleted(accountCard))
        }
    }

    private suspend fun getMandateFor(accountId: String): Mandate? {
        val mandate = getMandate(accountId).onFailure {
            _error.emit(it.message)
        }
        return mandate.getOrNull()
    }

    private fun getAccount(accountId: String) {
        viewModelScope.launch {
            val account = getCard(accountId)
            val mandate = getMandateFor(accountId)

            updateAccountName(account.name, account.cardBackgroundColor)

            _uiState.tryEmit(ShowAccount(account, mandate))
        }
    }

    private suspend fun acceptMandate(accountId: String, mandateId: String) {
        acceptPendingMandate(accountId = accountId, mandateId = mandateId)
            .onFailure {
                _error.tryEmit(it.message ?: "Something went wrong")
            }
            .onSuccess {
                val accountCard = getCard(accountId)
                val mandate = getMandateFor(accountId)
                _uiState.tryEmit(ShowAccount(accountCard, mandate))
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
