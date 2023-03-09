package io.snabble.pay.app.ui.screens.newaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.account.usecase.AccountManager
import io.snabble.pay.app.domain.mandate.usecase.MandateManager
import io.snabble.pay.app.ui.screens.detailsaccount.Loading
import io.snabble.pay.app.ui.screens.detailsaccount.ShowAccount
import io.snabble.pay.app.ui.screens.detailsaccount.UiState
import io.snabble.pay.mandate.domain.model.Mandate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAccountViewModel @Inject constructor(
    private val accountManager: AccountManager,
    private val mandateManager: MandateManager,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading(""))
    val uiState = _uiState.asStateFlow()

    private var _mandate = MutableStateFlow<Mandate?>(null)
    val mandate = _mandate.asStateFlow()

    init {
        getNewAccount()
    }

    fun createMandate(accountId: String) {
        viewModelScope.launch {
            _mandate.tryEmit(mandateManager.createMandate(accountId).getOrNull())
        }
    }

    fun acceptMandate(accountId: String, mandateId: String) {
        viewModelScope.launch {
            mandateManager.acceptMandate(accountId, mandateId)
        }
    }

    private fun getNewAccount() {
        viewModelScope.launch {
            _uiState.tryEmit(ShowAccount(accountManager.getAccountModels().last()))

        }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            accountManager.updateAccountName(id, name)
            _uiState.tryEmit(ShowAccount(accountManager.getAccountModel(id)))
        }
    }
}
