package io.snabble.pay.app.data.repository.account.localdatasource

import io.snabble.pay.app.data.database.AccountsDatabase
import io.snabble.pay.app.data.entity.AccountCard
import javax.inject.Inject

class AccountLocalDataSourceImpl @Inject constructor(
    private val db: AccountsDatabase,
) : AccountLocalDataSource {

    private val accountDao = db.accountDao()

    override suspend fun getAllAccounts(): List<AccountCard> =
        accountDao.getAllAccounts()

    override suspend fun getAccountById(id: String): AccountCard =
        accountDao.getAccountById(id)

    override suspend fun saveAccounts(accounts: List<AccountCard>) {
        accountDao.insertAllAccounts(accounts)
        for (acccount in accounts) {
            accountDao.updateAccount(acccount.id, acccount.mandateState)
        }
    }

    override suspend fun removeAccount(account: AccountCard) =
        accountDao.deleteAccount(account)

    override suspend fun deleteAllAccounts() =
        accountDao.clearAccounts()

    override suspend fun updateAccountName(id: String, name: String) =
        accountDao.updateAccountName(id, name)
}
