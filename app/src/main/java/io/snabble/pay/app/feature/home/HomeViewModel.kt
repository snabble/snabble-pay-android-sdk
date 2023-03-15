package io.snabble.pay.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.viewModelStates.Error
import io.snabble.pay.app.data.viewModelStates.Loading
import io.snabble.pay.app.data.viewModelStates.ShowAccounts
import io.snabble.pay.app.data.viewModelStates.UiState
import io.snabble.pay.app.domain.account.usecase.GetAllAccountCardsUseCase
import io.snabble.pay.app.domain.session.GetSessionTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccounts: GetAllAccountCardsUseCase,
    private val getSession: GetSessionTokenUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAccounts()
                .catch {
                    _uiState.tryEmit(Error(it.message))
                }
                .collect {
                    _uiState.tryEmit(ShowAccounts(it))
                }
        }
    }

    fun getSessionToken(accountId: String) {
        viewModelScope.launch {
            when (val state = uiState.value) {
                is ShowAccounts -> {
                    val accounts = state.accountCards.map { accMod ->
                        if (accMod.accountId == accountId) {
                            accMod.copy(qrCodeToken = getSession(accountId))
                        } else {
                            accMod
                        }
                    }
                    _uiState.tryEmit(ShowAccounts(accounts))
                }

                else -> {}
            }
        }
    }
}
