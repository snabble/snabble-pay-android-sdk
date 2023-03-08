package io.snabble.pay.app.ui.screens.verifyaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.core.SnabblePay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyAccountViewModel @Inject constructor(
    private val snabblePay: SnabblePay,
) : ViewModel() {

    private var _result =
        MutableStateFlow<Result<AccountCheck>>(Result.failure(IllegalArgumentException()))
    val result = _result.asStateFlow()
    fun getValidationLink() {
        viewModelScope.launch {

            val result = snabblePay.addNewAccount(
                appUri = "snabble-pay://account/check",
                city = "Berlin",
                twoLetterIsoCountryCode = "DE"
            )

            _result.tryEmit(result)

        }
    }
}
