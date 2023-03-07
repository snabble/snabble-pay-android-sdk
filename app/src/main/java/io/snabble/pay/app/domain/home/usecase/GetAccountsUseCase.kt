package io.snabble.pay.app.domain.home.usecase

import android.util.Log
import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.app.data.database.AccountsDatabase
import io.snabble.pay.app.data.entity.AccountCard
import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator
import io.snabble.pay.core.SnabblePay
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

interface GetAccountsUseCase {

    suspend operator fun invoke(): List<AccountCardModel>
}

class GetAccountsUseCaseImpl @Inject constructor(
    private val snabblePay: SnabblePay,
    private val db: AccountsDatabase
) : GetAccountsUseCase {

    override suspend fun invoke(): List<AccountCardModel> {
        val result = snabblePay.getAccounts()
        if (result.isSuccess && result.getOrDefault(emptyList()).isNotEmpty()) {
            coroutineScope {

                val accountsDao = db.accountDao()
                accountsDao.insertAllAccounts(result.toAccount())
                Log.d("xx", "invoke: ${accountsDao.getAllAccounts()}")
                accountsDao.clearAccounts()
                Log.d("xx", "invoke: ${accountsDao.getAllAccounts()}")

            }
        }
        return result.toAccountModel()
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

    private fun Result<List<Account>>.toAccount() =
        getOrDefault(emptyList()).map {
            AccountCard(
                id = it.id,
                holderName = it.holderName,
                iban = it.iban,
                bank = it.bank,
                name = it.name,
                currencyCode = it.currencyCode,
                createdAt = it.createdAt,
                mandateState = it.mandateState
            )
        }
}
