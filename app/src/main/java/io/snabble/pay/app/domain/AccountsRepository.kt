package io.snabble.pay.app.domain

import io.snabble.pay.app.data.entity.AccountCard

interface AccountsRepository {

    suspend fun getAccounts(): List<AccountCard>

    suspend fun saveAccounts(): List<AccountCard>
}
