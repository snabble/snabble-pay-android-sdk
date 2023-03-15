package io.snabble.pay.app.feature.detailsaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.viewModelStates.Error
import io.snabble.pay.app.data.viewModelStates.Loading
import io.snabble.pay.app.data.viewModelStates.ShowAccount
import io.snabble.pay.app.data.viewModelStates.UiState
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsAccountViewModel @Inject constructor(
    private val getCard: GetAccountCardUseCase,
    private val setCardLabel: SetAccountCardLabelUseCase,
    private val getMandate: GetMandateUseCase,
    private val acceptPendingMandate: AcceptMandateUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    fun getAccount(accountId: String) {
        viewModelScope.launch {
            _uiState.tryEmit(ShowAccount(getCard(accountId)))
        }
    }

    fun acceptMandate(accountId: String) {
        viewModelScope.launch {
            getMandate(accountId = accountId)
                .onFailure {
                    _uiState.tryEmit(Error(it.message))
                }
                .onSuccess {
                    if (it != null) {
                        acceptMandate(accountId = accountId, it.id)
                    } else {
                        _uiState.tryEmit(Error("No Mandate exists"))
                    }
                }
        }
    }

    private suspend fun acceptMandate(accountId: String, mandateId: String) {
        acceptPendingMandate(accountId = accountId, mandateId = mandateId)
            .onFailure {
                _uiState.tryEmit(Error(it.message ?: "Something went wrong"))
            }
            .onSuccess {
                val accountCard = getCard(accountId)
                _uiState.tryEmit(ShowAccount(accountCard))
            }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            setCardLabel(id, name)
//            _uiState.tryEmit(ShowAccount(accountManager.getAccountModel(id)))
        }
    }
}
