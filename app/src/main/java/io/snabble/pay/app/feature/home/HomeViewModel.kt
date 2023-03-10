package io.snabble.pay.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.viewModelStates.Loading
import io.snabble.pay.app.data.viewModelStates.ShowAccounts
import io.snabble.pay.app.data.viewModelStates.UiState
import io.snabble.pay.app.domain.account.AccountCardModel
import io.snabble.pay.app.domain.account.usecase.GetAccountsUseCase
import io.snabble.pay.app.domain.session.GetSessionTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccounts: GetAccountsUseCase,
    private val getSession: GetSessionTokenUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private var cardList: List<AccountCardModel> = emptyList()

    init {
        viewModelScope.launch {
            val accounts = getAccounts()
            cardList = accounts
            _uiState.tryEmit(ShowAccounts(accounts))
        }
    }

    fun getSessionToken(accountId: String) {
        viewModelScope.launch {
            val accounts = cardList.map { accMod ->
                if (accMod.accountId == accountId) {
                    accMod.copy(qrCodeToken = getSession(accountId))
                } else {
                    accMod
                }
            }
            _uiState.tryEmit(ShowAccounts(accounts))
        }
    }
}

