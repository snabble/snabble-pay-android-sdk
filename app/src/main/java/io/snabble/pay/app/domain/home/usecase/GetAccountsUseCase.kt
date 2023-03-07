package io.snabble.pay.app.domain.home.usecase

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator
import io.snabble.pay.core.SnabblePay
import javax.inject.Inject

interface GetAccountsUseCase {

    suspend operator fun invoke(): List<AccountCardModel>
}

class GetAccountsUseCaseImpl @Inject constructor(
    private val snabblePay: SnabblePay
) : GetAccountsUseCase {

    override suspend fun invoke(): List<AccountCardModel> {
        return snabblePay.getAccounts().toAccountModel()
    }

    private fun Result<List<Account>>.toAccountModel() =
        getOrDefault(emptyList()).map {
            AccountCardModel(
                accountId = it.id,
                holderName = it.holderName,
                iban = it.iban,
                bank = it.bank,
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = null
            )
        }
}
