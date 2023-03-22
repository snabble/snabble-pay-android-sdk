package io.snabble.pay.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.AddAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAllAccountCardsUseCase
import io.snabble.pay.app.domain.session.GetSessionTokenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccounts: GetAllAccountCardsUseCase,
    private val getSession: GetSessionTokenUseCase,
    private val addAccountUseCase: AddAccountUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _validationLink = MutableSharedFlow<String>()
    val validationLink = _validationLink.asSharedFlow()

    private val _error = MutableSharedFlow<ErrorResponse?>()
    val error = _error.asSharedFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            getAccounts()
                .collect {
                    when (it) {
                        is AppError -> _error.emit(it.value)
                        is AppSuccess -> {
                            if (it.value.isEmpty()) {
                                _uiState.tryEmit(AddNewCart)
                            } else {
                                _uiState.tryEmit(ShowAccounts(it.value))
                            }
                        }
                    }
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

    fun getValidationLink() {
        viewModelScope.launch {
            val result = addAccountUseCase(
                appUri = "snabble-pay://account/check",
                city = "Berlin",
                twoLetterIsoCountryCode = "DE"
            )
            when (result) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> _validationLink.emit(result.value.validationLink)
            }
        }
    }
}

sealed interface UiState

object Loading : UiState

object AddNewCart : UiState

data class ShowAccounts(
    val accountCards: List<AccountCard>,
) : UiState
