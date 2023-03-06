package io.snabble.pay.app.domain.home.usecase

import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator

interface GetAccountsUseCase {

    suspend operator fun invoke(): List<AccountCardModel>
}

class GetAccountsUseCaseImpl : GetAccountsUseCase {

    // get account + get session
    override suspend fun invoke(): List<AccountCardModel> {
        return listOf(
            AccountCardModel(
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = null,
                accountId = 1,
                holderName = "Petra MusterMann",
                iban = "DE91 1000 0000 0123 4567 89",
                bank = "Deutsche Bank"
            ),
            AccountCardModel(
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = null,
                accountId = 2,
                holderName = "Muster Mann",
                iban = "DE 1234 1234 1234 1234",
                bank = "Deutsche Bank"
            ),
            AccountCardModel(
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = null,
                holderName = "Muster Mann",
                accountId = 3,
                iban = "DE 1234 1234 1234 1234",
                bank = "Deutsche Bank"
            )
        )
    }
}
