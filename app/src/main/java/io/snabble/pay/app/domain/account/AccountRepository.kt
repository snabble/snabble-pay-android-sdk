package io.snabble.pay.app.domain.account

import io.snabble.pay.app.data.entity.AccountCard

interface AccountRepository {

    suspend fun getAccounts(): List<AccountCard>

    suspend fun getAccount(id: String): AccountCard

    suspend fun updateAccountName(id: String, name: String)

    suspend fun saveAccounts(): List<AccountCard>
}
