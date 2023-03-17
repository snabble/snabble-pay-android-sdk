package io.snabble.pay.app.feature.verifyaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.account.usecase.AddAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()
    fun getValidationLink() {
        viewModelScope.launch {
            addAccountUseCase(
                appUri = "snabble-pay://account/check",
                city = "Berlin",
                twoLetterIsoCountryCode = "DE"
            ).onSuccess {
                _uiState.tryEmit(StartValidationFlow(it.validationLink))
            }.onFailure {
                _uiState.tryEmit(Error(it.message))
            }
        }
    }
}

sealed interface UiState

object Loading : UiState

data class StartValidationFlow(
    val validationLink: String,
) : UiState

data class Error(
    val message: String? = "Ups! Something went wrong",
) : UiState
