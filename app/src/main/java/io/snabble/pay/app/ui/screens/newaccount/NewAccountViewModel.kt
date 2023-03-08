package io.snabble.pay.app.ui.screens.newaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.AccountsRepository
import io.snabble.pay.app.domain.accountCard.toAccountCardModel
import io.snabble.pay.app.ui.screens.detailsaccount.Loading
import io.snabble.pay.app.ui.screens.detailsaccount.ShowAccount
import io.snabble.pay.app.ui.screens.detailsaccount.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAccountViewModel @Inject constructor(
    private val repository: AccountsRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading(""))
    val uiState = _uiState.asStateFlow()

    init {
        getNewAccount()
    }

    private fun getNewAccount() {
        viewModelScope.launch {
            _uiState.tryEmit(ShowAccount(repository.getAccounts().last().toAccountCardModel()))
        }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            repository.updateAccountName(id, name)
            _uiState.tryEmit(ShowAccount(repository.getAccount(id).toAccountCardModel()))
        }
    }
}
