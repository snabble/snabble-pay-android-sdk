package io.snabble.pay.app.data.repository.localdatasource

import io.snabble.pay.app.data.database.AccountsDatabase
import io.snabble.pay.app.data.entity.AccountCard
import javax.inject.Inject

class AccountsLocalDataSourceImpl @Inject constructor(
    private val db: AccountsDatabase
) : AccountsLocalDataSource {

    val accountDao = db.accountDao()

    override suspend fun getAllAccounts(): List<AccountCard> =
        accountDao.getAllAccounts()

    override suspend fun getAccountById(id: String): AccountCard =
        accountDao.getAccountById(id)

    override suspend fun saveAccounts(accounts: List<AccountCard>) =
        accountDao.insertAllAccounts(accounts)

    override suspend fun removeAccount(account: AccountCard) =
        accountDao.deleteAccount(account)

    override suspend fun deleteAllAccounts() =
        accountDao.clearAccounts()
}
