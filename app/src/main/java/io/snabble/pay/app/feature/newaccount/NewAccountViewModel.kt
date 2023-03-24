package io.snabble.pay.app.feature.newaccount

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.ErrorResponse
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
    private val getCard: GetAccountCardUseCase,
    private val setCardLabel: SetAccountCardLabelUseCase,
    private val getMandate: GetMandateUseCase,
    private val createMandateUseCase: CreateMandateUseCase,
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

    fun acceptMandate() {
        viewModelScope.launch {
            when (val result = getMandate(accountId = accountId)) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> {
                    val mandate = result.value
                    if (mandate != null) {
                        acceptMandate(accountId, mandate.id)
                        getAccount(accountId)
                    } else {
                        _error.tryEmit(null)
                    }
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

    private fun getAccount(accountId: String) {
        viewModelScope.launch {
            when (val result = getCard(accountId)) {
                is AppError -> _error.emit(result.value)
                is AppSuccess -> {
                    val account = result.value
                    if (account.mandateState == MandateState.MISSING) {
                        createMandate(accountId)
                    }
                    val mandate = getMandateFor(accountId)

                    updateAccountName(account.name, account.cardBackgroundColor)

                    _uiState.tryEmit(ShowAccount(account, mandate))
                }
            }
        }
    }

    private suspend fun createMandate(accountId: String) {
        when (val result = createMandateUseCase(accountId)) {
            is AppError -> _error.emit(result.value)
            is AppSuccess -> return
        }
    }

    private suspend fun acceptMandate(accountId: String, mandateId: String) {
        when (val result = acceptPendingMandate(accountId = accountId, mandateId = mandateId)) {
            is AppError -> _error.emit(result.value)
            is AppSuccess -> {
                when (val accountCardResult = getCard(accountId)) {
                    is AppError -> _error.emit(accountCardResult.value)
                    is AppSuccess -> {
                        val mandate = getMandateFor(accountId)
                        _uiState.tryEmit(ShowAccount(accountCardResult.value, mandate))
                    }
                }
            }
        }
    }

    fun updateAccountName(name: String, colors: List<String>) {
        viewModelScope.launch {
            setCardLabel(accountId = accountId, name = name, colors = colors)
        }
    }
}

sealed interface UiState

object Loading : UiState

data class ShowAccount(
    val accountCard: AccountCard,
    val mandate: Mandate?,
) : UiState
