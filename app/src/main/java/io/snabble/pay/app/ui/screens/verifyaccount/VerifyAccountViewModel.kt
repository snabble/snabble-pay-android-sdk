package io.snabble.pay.app.ui.screens.verifyaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.app.domain.account.usecase.AddNewAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyAccountViewModel @Inject constructor(
    private val addNewAccountUseCase: AddNewAccountUseCase,
) : ViewModel() {

    private var _result = MutableStateFlow<AccountCheck?>(null)
    val result = _result.asStateFlow()
    fun getValidationLink() {
        viewModelScope.launch {
            _result.tryEmit(
                addNewAccountUseCase(
                    appUri = "snabble-pay://account/check",
                    city = "Berlin",
                    twoLetterIsoCountryCode = "DE"
                )
            )
        }
    }
}
