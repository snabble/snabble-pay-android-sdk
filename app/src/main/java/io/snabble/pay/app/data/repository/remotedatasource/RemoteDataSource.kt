package io.snabble.pay.app.data.repository.remotedatasource

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck

interface RemoteDataSource {

    suspend fun getAllAccounts(): Result<List<Account>>
    suspend fun getAccount(id: String): Result<Account>
    suspend fun addAccounts(): Result<AccountCheck>
}
