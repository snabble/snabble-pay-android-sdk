package io.snabble.pay.app.ui.screens.detailsaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.AccountsRepository
import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.toAccountCardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsAccountViewModel @Inject constructor(
    private val repository: AccountsRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading(""))
    val uiState = _uiState.asStateFlow()

    fun getAccount(id: String) {
        viewModelScope.launch {
            _uiState.tryEmit(ShowAccount(repository.getAccount(id).toAccountCardModel()))
        }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            repository.updateAccountName(id, name)
            _uiState.tryEmit(ShowAccount(repository.getAccount(id).toAccountCardModel()))
        }
    }
}

sealed interface UiState
data class Loading(val name: String) : UiState
data class ShowAccount(val accountCardModel: AccountCardModel) : UiState
