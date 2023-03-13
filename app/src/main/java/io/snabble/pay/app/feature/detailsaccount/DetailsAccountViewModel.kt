package io.snabble.pay.app.feature.detailsaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.viewModelStates.Error
import io.snabble.pay.app.data.viewModelStates.Loading
import io.snabble.pay.app.data.viewModelStates.ShowAccount
import io.snabble.pay.app.data.viewModelStates.UiState
import io.snabble.pay.app.domain.account.usecase.AccountManager
import io.snabble.pay.app.domain.mandate.usecase.MandateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsAccountViewModel @Inject constructor(
    private val accountManager: AccountManager,
    private val mandateManager: MandateManager,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    fun getAccount(accountId: String) {
        viewModelScope.launch {
            _uiState.tryEmit(ShowAccount(accountManager.getAccountModel(accountId)))
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
                _uiState.tryEmit(Error(it.message ?: "Something went wrong"))
            }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            accountManager.updateAccountName(id, name)
            _uiState.tryEmit(ShowAccount(accountManager.getAccountModel(id)))
        }
    }
}
