package io.snabble.pay.app.domain.accountCard.usecase

import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator

interface GetAccountInformationUseCase {

    suspend operator fun invoke(): List<AccountCardModel>
}

class GetAccountInformationUseCaseImpl : GetAccountInformationUseCase {

    // get account + get session
    override suspend fun invoke(): List<AccountCardModel> {
        return listOf(
            AccountCardModel(
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = "test",
                holderName = "Petra MusterMann",
                iban = "DE91 1000 0000 0123 4567 89",
                bank = "Deutsche Bank"
            ),
            AccountCardModel(
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = "test",
                holderName = "Muster Mann",
                iban = "DE 1234 1234 1234 1234",
                bank = "Deutsche Bank"
            ),
            AccountCardModel(
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = "test",
                holderName = "Muster Mann",
                iban = "DE 1234 1234 1234 1234",
                bank = "Deutsche Bank"
            )
        )
    }
}
