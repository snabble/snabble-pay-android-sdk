package io.snabble.pay.app.domain.account

import io.snabble.pay.account.domain.model.AccountCheck
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAccount(id: String): AccountCard

    fun getAccounts(): Flow<List<AccountCard>>

    suspend fun setAccountLabel(id: String, label: String)
}
