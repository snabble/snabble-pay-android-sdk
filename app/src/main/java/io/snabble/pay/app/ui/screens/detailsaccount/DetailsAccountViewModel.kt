package io.snabble.pay.app.ui.screens.detailsaccount

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.account.AccountCardModel
import io.snabble.pay.app.domain.account.usecase.GetAccountUseCase
import io.snabble.pay.app.domain.account.usecase.UpdateAccountNameUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.mandate.domain.model.Mandate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsAccountViewModel @Inject constructor(
    private val createMandateUseCase: CreateMandateUseCase,
    private val getMandateUseCase: GetMandateUseCase,
    private val acceptMandateUseCase: AcceptMandateUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountNameUseCase: UpdateAccountNameUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading(""))
    val uiState = _uiState.asStateFlow()

    private var _mandate = MutableStateFlow<Mandate?>(null)
    val mandate = _mandate.asStateFlow()

    fun getAccount(id: String) {
        viewModelScope.launch {
            _mandate.tryEmit(getMandateUseCase(id).getOrNull().also {
                Log.d("xx", "getAccount: ${it?.state}")
            })
            _uiState.tryEmit(ShowAccount(getAccountUseCase(id)))
        }
    }

    fun createMandate(accountId: String) {
        viewModelScope.launch {
            _mandate.tryEmit(createMandateUseCase(accountId = accountId).getOrNull().also {
                Log.d("xx", "createMandate: ${it?.id}")
            })
        }
    }

    fun acceptMandate(accountId: String, mandateId: String) {
        viewModelScope.launch {
            _mandate.tryEmit(acceptMandateUseCase(accountId, mandateId).getOrNull())
        }
    }

    fun mandateState(id: String) {
        viewModelScope.launch {
            Log.d("xx", "mandateState: ${getMandateUseCase(id).getOrNull()?.state} ")
        }
    }

    fun updateAccountName(id: String, name: String) {
        viewModelScope.launch {
            updateAccountNameUseCase(id, name)
            _uiState.tryEmit(ShowAccount(getAccountUseCase(id)))
        }
    }
}

sealed interface UiState
data class Loading(val name: String) : UiState
data class ShowAccount(
    val accountCardModel: AccountCardModel,
) : UiState

