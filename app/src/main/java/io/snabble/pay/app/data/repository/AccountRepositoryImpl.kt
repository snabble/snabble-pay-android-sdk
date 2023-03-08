package io.snabble.pay.app.data.repository

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.app.data.entity.AccountCard
import io.snabble.pay.app.data.repository.localdatasource.AccountsLocalDataSource
import io.snabble.pay.app.data.repository.remotedatasource.RemoteDataSource
import io.snabble.pay.app.domain.AccountsRepository
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val localDataSource: AccountsLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : AccountsRepository {

    override suspend fun getAccounts(): List<AccountCard> {
        val result = remoteDataSource.getAllAccounts()
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
