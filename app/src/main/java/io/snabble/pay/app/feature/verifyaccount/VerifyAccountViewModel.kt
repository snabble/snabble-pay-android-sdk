package io.snabble.pay.app.feature.verifyaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.data.viewModelStates.Loading
import io.snabble.pay.app.data.viewModelStates.StartValidationFlow
import io.snabble.pay.app.data.viewModelStates.UiState
import io.snabble.pay.app.domain.account.usecase.AddNewAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyAccountViewModel @Inject constructor(
    private val addNewAccountUseCase: AddNewAccountUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(Loading)
    val uiState = _uiState.asStateFlow()
    fun getValidationLink() {
        viewModelScope.launch {
            addNewAccountUseCase(
                appUri = "snabble-pay://account/check",
                city = "Berlin",
                twoLetterIsoCountryCode = "DE"
            ).onSuccess {
                _uiState.tryEmit(StartValidationFlow(it.validationLink))
            }.onFailure {
                _uiState.tryEmit(
                    io.snabble.pay.app.data.viewModelStates.Error(
                        it.message ?: "Something went wront"
                    )
                )

            }
        }
    }
}
