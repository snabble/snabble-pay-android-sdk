package io.snabble.pay.account.domain.repository

import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.core.util.Result
import io.snabble.pay.shared.account.domain.model.Account

/** @suppress Dokka */
interface AccountsRepository {

    suspend fun getAccount(id: String): Result<Account>

    suspend fun getAccountCheck(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAccounts(): Result<List<Account>>

    suspend fun removeAccount(id: String): Result<Account>
}
