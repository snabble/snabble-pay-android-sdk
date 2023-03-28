package io.snabble.pay.app.feature.newaccount

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.data.utils.onError
import io.snabble.pay.app.data.utils.onSuccess
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.mandate.domain.model.Mandate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAccountViewModel @Inject constructor(
    private val getAccountCard: GetAccountCardUseCase,
    private val setCardLabelAndColors: SetAccountCardLabelUseCase,
    private val getMandate: GetMandateUseCase,
    private val createMandate: CreateMandateUseCase,
    private val acceptPendingMandate: AcceptMandateUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()

    private val _error = MutableSharedFlow<ErrorResponse?>()
    val error = _error.asSharedFlow()

    val accountId: String = requireNotNull(savedStateHandle["accountId"])

    init {
        getAccount(accountId)
    }

    private fun getAccount(accountId: String) {
        viewModelScope.launch {
            getAccountCard(accountId = accountId)
                .onError { _error.emit(it) }
                .onSuccess { account ->
                    val mandate =
                        if (account.mandateState == MandateState.MISSING) {
                            createMandateFor(accountId)
                        } else {
                            getMandateFor(accountId)
                        }

                    updateAccountName(account.name, account.cardBackgroundColor)

                    _uiState.tryEmit(ShowAccount(account, mandate))
                }
        }
    }

    fun acceptMandate() {
        viewModelScope.launch {
            getMandate(accountId = accountId)
                .onError { _error.emit(it) }
                .onSuccess { mandate ->
                    if (mandate != null) {
                        acceptMandate(accountId, mandate.id)
                    } else {
                        _error.tryEmit(null)
                    }
                }
        }
    }

    private suspend fun getMandateFor(accountId: String): Mandate? {
        return when (val result = getMandate(accountId)) {
            is AppError -> {
                _error.emit(result.value)
                null
            }
            is AppSuccess -> result.value
        }
    }

    private suspend fun createMandateFor(accountId: String): Mandate? {
        return when (val result = createMandate(accountId = accountId)) {
            is AppError -> {
                _error.emit(result.value)
                null
            }
            is AppSuccess -> result.value
        }
    }

    private suspend fun acceptMandate(accountId: String, mandateId: String) {
        acceptPendingMandate(accountId = accountId, mandateId = mandateId)
            .onError { _error.emit(it) }
            .onSuccess { mandate ->
                getAccountCard(accountId)
                    .onError { _error.emit(it) }
                    .onSuccess { account ->
                        _uiState.tryEmit(ShowAccount(account, mandate))
                    }
            }
    }

    fun updateAccountName(name: String, colors: List<String>) {
        viewModelScope.launch {
            setCardLabelAndColors(accountId = accountId, name = name, colors = colors)
        }
    }
}

sealed interface UiState

object Loading : UiState

data class ShowAccount(
    val accountCard: AccountCard,
    val mandate: Mandate?,
) : UiState
