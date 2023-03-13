package io.snabble.pay.app.domain.account.usecase

import android.util.Log
import io.snabble.pay.app.data.entity.AccountCard
import io.snabble.pay.app.domain.account.AccountCardModel
import io.snabble.pay.app.domain.account.AccountRepository
import javax.inject.Inject

interface GetAccountsUseCase {

    suspend operator fun invoke(): Result<List<AccountCardModel>>
}

class GetAccountsUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
) : GetAccountsUseCase {

    override suspend fun invoke(): Result<List<AccountCardModel>> =
        accountRepository.getAccounts().toAccountModel().also {
            Log.d("xx", "invoke: ${it.getOrNull()}")
        }

    private fun Result<List<AccountCard>>.toAccountModel() =
        map {
            it.map {
                AccountCardModel(
                    accountId = it.id,
                    holderName = it.holderName,
                    iban = it.iban,
                    bank = it.bank,
                    name = it.name,
                    mandateState = it.mandateState,
                    cardBackgroundColor = it.colors,
                    qrCodeToken = null
                )
            }
        }
}
