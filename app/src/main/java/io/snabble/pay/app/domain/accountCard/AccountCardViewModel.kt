package io.snabble.pay.app.domain.accountCard

import androidx.lifecycle.ViewModel
import io.snabble.pay.app.domain.accountCard.usecase.GetAccountInformationUseCaseImpl
import kotlinx.coroutines.runBlocking

class AccountCardViewModel : ViewModel() {

    private val _accountCardList =
        runBlocking { GetAccountInformationUseCaseImpl().invoke() }
    val accountCardList = _accountCardList
}
