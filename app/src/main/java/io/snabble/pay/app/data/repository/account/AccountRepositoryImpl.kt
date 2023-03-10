package io.snabble.pay.app.data.repository.account

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.app.data.entity.AccountCard
import io.snabble.pay.app.data.repository.account.localdatasource.AccountLocalDataSource
import io.snabble.pay.app.data.repository.account.remotedatasource.AccountRemoteDataSource
import io.snabble.pay.app.domain.account.AccountRepository
import io.snabble.pay.app.domain.account.utils.GradiantGenerator
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val localDataSource: AccountLocalDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource,
) : AccountRepository {

    override suspend fun getAccounts(): List<AccountCard> {
        val result = accountRemoteDataSource.getAllAccounts()
        if (result.isSuccess && result.getOrDefault(emptyList()).isNotEmpty()) {
            localDataSource.saveAccounts(result.toAccount())
        }
        return localDataSource.getAllAccounts()
    }

    override suspend fun getAccount(id: String): AccountCard =
        localDataSource.getAccountById(id)

    override suspend fun updateAccountName(id: String, name: String) {
        localDataSource.updateAccountName(id, name)
    }

    override suspend fun saveAccounts(): List<AccountCard> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): AccountCheck? =
        accountRemoteDataSource.addNewAccount(appUri, city, twoLetterIsoCountryCode).getOrNull()

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
                mandateState = it.mandateState,
                colors = GradiantGenerator().createGradiantBackground()
            )
        }
}
