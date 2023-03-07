package io.snabble.pay.app.domain.home.usecase

import io.snabble.pay.app.data.entity.AccountCard
import io.snabble.pay.app.domain.AccountsRepository
import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator
import javax.inject.Inject

interface GetAccountsUseCase {

    suspend operator fun invoke(): List<AccountCardModel>
}

class GetAccountsUseCaseImpl @Inject constructor(
    private val accountsRepository: AccountsRepository,
) : GetAccountsUseCase {

    override suspend fun invoke(): List<AccountCardModel> =
        accountsRepository.getAccounts().toAccountModel()

    private fun List<AccountCard>.toAccountModel() =
        map {
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
