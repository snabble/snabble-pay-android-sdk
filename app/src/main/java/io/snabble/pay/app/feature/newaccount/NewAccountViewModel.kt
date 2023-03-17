package io.snabble.pay.app.feature.newaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.GetAllAccountCardsUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateState.ACCEPTED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAccountViewModel @Inject constructor(
    private val getAccountCard: GetAccountCardUseCase,
    private val getAllAccountCards: GetAllAccountCardsUseCase,
    private val setCardLabel: SetAccountCardLabelUseCase,
    private val createNewMandate: CreateMandateUseCase,
    private val getMandate: GetMandateUseCase,
    private val acceptPendingMandate: AcceptMandateUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getNewAccount()
    }

    private fun createMandate(accountId: String) {
        viewModelScope.launch {
            createNewMandate(accountId = accountId)
                .onFailure {
                    _uiState.tryEmit(Error(it.message))
                }
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
                        acceptMandate(accountId, it.id)
                    } else {
                        _uiState.tryEmit(Error("No Mandate exists"))
                    }
                }
        }
    }

    private suspend fun acceptMandate(accountId: String, mandateId: String) {
        acceptPendingMandate(accountId = accountId, mandateId = mandateId)
            .onFailure {
                _uiState.tryEmit(Error(it.message))
            }
            .onSuccess {
                val accountCard = getAccountCard(accountId = accountId)
                _uiState.tryEmit(ShowAccount(accountCard))
            }
    }

    private fun getNewAccount() {
        viewModelScope.launch {
            getAllAccountCards()
                .catch {
                    _uiState.tryEmit(Error(it.message))
                }
                .collectLatest { cards ->
                    val newAccount = cards.last()
                    _uiState.tryEmit(ShowAccount(newAccount))
                    getMandate(accountId = newAccount.accountId)
                        .onFailure {
                            _uiState.tryEmit(Error(it.message))
                        }
                        .onSuccess { mandate ->
                            checkMandateState(mandate, newAccount)
                        }
                }
        }
    }

    private fun checkMandateState(
        mandate: Mandate?,
        newAccountCard: AccountCard,
    ) {
        if (mandate == null) {
            createMandate(accountId = newAccountCard.accountId)
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
            setCardLabel(accountId = id, name = name)
            val accountCard = getAccountCard(accountId = id)
            _uiState.tryEmit(ShowAccount(accountCard))
        }
    }
}

sealed interface UiState

object Loading : UiState

object MandateAccepted : UiState

object MandatePendingOrDeclined : UiState

data class ShowAccount(
    val accountCard: AccountCard,
) : UiState

data class Error(
    val message: String? = "Ups! Something went wrong",
) : UiState
