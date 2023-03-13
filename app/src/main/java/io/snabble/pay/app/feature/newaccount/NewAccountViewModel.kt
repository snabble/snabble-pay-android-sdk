package io.snabble.pay.app.feature.newaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.viewModelStates.Error
import io.snabble.pay.app.data.viewModelStates.Loading
import io.snabble.pay.app.data.viewModelStates.MandateAccepted
import io.snabble.pay.app.data.viewModelStates.MandatePendingOrDeclined
import io.snabble.pay.app.data.viewModelStates.ShowAccount
import io.snabble.pay.app.data.viewModelStates.UiState
import io.snabble.pay.app.domain.account.AccountCardModel
import io.snabble.pay.app.domain.account.usecase.AccountManager
import io.snabble.pay.app.domain.mandate.usecase.MandateManager
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateState.ACCEPTED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAccountViewModel @Inject constructor(
    private val accountManager: AccountManager,
    private val mandateManager: MandateManager,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getNewAccount()
    }

    private fun createMandate(accountId: String) {
        viewModelScope.launch {
            mandateManager.createMandate(accountId).onFailure {
                _uiState.tryEmit(Error(it.message))
            }
        }
    }

    fun acceptMandate(accountId: String) {
        viewModelScope.launch {
            mandateManager.getMandate(accountId)
                .onSuccess {
                    if (it != null) {
                        acceptMandate(accountId, it.id)
                    } else {
                        _uiState.tryEmit(Error("No Mandate exists"))
                    }
                }
                .onFailure {
                    _uiState.tryEmit(Error(it.message))
                }
        }
    }

    private suspend fun acceptMandate(accountId: String, mandateId: String) {
        mandateManager.acceptMandate(accountId, mandateId)
            .onSuccess {
                _uiState.tryEmit(
                    ShowAccount(
                        accountManager.getAccountModel(accountId)
                    )
                )
            }
            .onFailure {
                _uiState.tryEmit(Error(it.message))
            }
    }

    private fun getNewAccount() {
        viewModelScope.launch {
            accountManager.getAccountModels()
                .onFailure {
                    _uiState.tryEmit(Error(it.message))
                }
                .onSuccess { accounts ->
                    val newAccount = accounts.last()
                    _uiState.tryEmit(ShowAccount(newAccount))
                    mandateManager.getMandate(newAccount.accountId)
                        .onSuccess { mandate ->
                            checkMandateState(mandate, newAccount)
                        }.onFailure {
                            _uiState.tryEmit(Error(it.message))
                        }
                }
        }
    }

    private fun checkMandateState(
        mandate: Mandate?,
        newAccount: AccountCardModel,
    ) {
        if (mandate == null) {
            createMandate(newAccount.accountId)
        } else {
            if (mandate.state == ACCEPTED) {
                _uiState.tryEmit(MandateAccepted)
            } else {
                _uiState.tryEmit(MandatePendingOrDeclined)
            }
        }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            accountManager.updateAccountName(id, name)
            _uiState.tryEmit(ShowAccount(accountManager.getAccountModel(id)))
        }
    }
}
