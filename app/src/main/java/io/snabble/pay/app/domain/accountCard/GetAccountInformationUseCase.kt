package io.snabble.pay.app.domain.accountCard

import io.snabble.pay.app.domain.QrCodeGenerator

interface GetAccountInformationUseCase {

    suspend operator fun invoke(): AccountCardModel
}

class GetAccountInformationUseCaseImpl: GetAccountInformationUseCase {

    // get account + get session
    override suspend fun invoke(): AccountCardModel {
        return  AccountCardModel(
            cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
            qrCodeToken = QrCodeGenerator.generateQrCode("test"),
            holderName = "Muster Mann",
            iban = "DE 1234 1234 1234 1234",
            bank = "Deutsche Bank"
        )
    }
}