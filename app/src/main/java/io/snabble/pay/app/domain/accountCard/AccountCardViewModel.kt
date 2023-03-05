package io.snabble.pay.app.domain.accountCard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.accountCard.usecase.GetAccountInformationUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AccountCardViewModel @Inject constructor() : ViewModel() {

    private val _accountCardList =
        MutableStateFlow(runBlocking { GetAccountInformationUseCaseImpl().invoke() })
    val accountCardList = _accountCardList.asStateFlow()
}
