package io.snabble.pay.account.domain.repository

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck

interface AccountsRepository {

    suspend fun getAccountCheck(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAccount(id: String): Result<Account>

    suspend fun getAccounts(): Result<List<Account>>

    suspend fun removeAccount(id: String): Result<Account>
}
