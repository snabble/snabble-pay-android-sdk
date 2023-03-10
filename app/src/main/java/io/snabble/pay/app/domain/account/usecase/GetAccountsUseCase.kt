package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.app.data.entity.AccountCard
import io.snabble.pay.app.domain.account.AccountCardModel
import io.snabble.pay.app.domain.account.AccountRepository
import javax.inject.Inject

interface GetAccountsUseCase {

    suspend operator fun invoke(): List<AccountCardModel>
}

class GetAccountsUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
) : GetAccountsUseCase {

    override suspend fun invoke(): List<AccountCardModel> =
        accountRepository.getAccounts().toAccountModel()

    private fun List<AccountCard>.toAccountModel() =
        map {
            AccountCardModel(
                accountId = it.id,
                holderName = it.holderName,
                iban = it.iban,
                bank = it.bank,
                name = it.name,
                cardBackgroundColor = it.colors,
                qrCodeToken = null
            )
        }
}
