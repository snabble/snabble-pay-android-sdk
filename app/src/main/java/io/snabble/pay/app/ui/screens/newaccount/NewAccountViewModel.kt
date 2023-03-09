package io.snabble.pay.app.ui.screens.newaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.account.usecase.GetAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountsUseCase
import io.snabble.pay.app.domain.account.usecase.UpdateAccountNameUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
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
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val createMandateUseCase: CreateMandateUseCase,
    private val acceptMandateUseCase: AcceptMandateUseCase,
    private val updateAccountNameUseCase: UpdateAccountNameUseCase,
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
            _mandate.tryEmit(createMandateUseCase(accountId = accountId).getOrNull())
        }
    }

    fun acceptMandate(accountId: String, mandateId: String) {
        viewModelScope.launch {
            acceptMandateUseCase(accountId, mandateId)
        }
    }

    private fun getNewAccount() {
        viewModelScope.launch {
            _uiState.tryEmit(ShowAccount(getAccountsUseCase().last()))

        }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            updateAccountNameUseCase(id, name)
            _uiState.tryEmit(ShowAccount(getAccountUseCase(id)))
        }
    }
}
