package io.snabble.pay.app.domain.accountCard

import androidx.lifecycle.ViewModel
import io.snabble.pay.app.domain.accountCard.GetAccountInformationUseCaseImpl
import kotlinx.coroutines.runBlocking

class AccountCardViewModel: ViewModel() {

    private val accountCard_ = runBlocking {GetAccountInformationUseCaseImpl().invoke()}
    val accountCard = accountCard_
}