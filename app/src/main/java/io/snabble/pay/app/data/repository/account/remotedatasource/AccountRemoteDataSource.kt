package io.snabble.pay.app.data.repository.account.remotedatasource

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck

interface AccountRemoteDataSource {

    suspend fun getAllAccounts(): Result<List<Account>>
    suspend fun getAccount(id: String): Result<Account>
    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>
}
