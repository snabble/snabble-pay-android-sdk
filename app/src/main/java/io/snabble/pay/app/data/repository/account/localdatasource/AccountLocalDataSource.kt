package io.snabble.pay.app.data.repository.account.localdatasource

import io.snabble.pay.app.data.entity.AccountCard

interface AccountLocalDataSource {

    suspend fun getAllAccounts(): List<AccountCard>

    suspend fun getAccountById(id: String): AccountCard

    suspend fun saveAccounts(accounts: List<AccountCard>)

    suspend fun removeAccount(account: AccountCard)

    suspend fun deleteAllAccounts()

    suspend fun updateAccountName(id: String, name: String)
}
