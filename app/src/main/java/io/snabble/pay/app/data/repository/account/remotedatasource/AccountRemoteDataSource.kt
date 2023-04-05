package io.snabble.pay.app.data.repository.account.remotedatasource

import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.core.util.Result
import io.snabble.pay.shared.account.domain.model.Account

interface AccountRemoteDataSource {

    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAllAccounts(): Result<List<Account>>

    suspend fun getAccount(id: String): Result<Account>

    suspend fun deleteAccount(id: String): Result<Account>
}
