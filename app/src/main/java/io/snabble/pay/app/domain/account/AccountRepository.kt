package io.snabble.pay.app.domain.account

import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.app.data.utils.AppResult
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): AppResult<AccountCheck>

    suspend fun deleteAccount(id: String): AppResult<AccountCard>

    suspend fun getAccount(id: String): AppResult<AccountCard>

    fun getAccounts(): Flow<AppResult<List<AccountCard>>>

    suspend fun setAccountLabel(id: String, label: String, colors: List<String>)
}

