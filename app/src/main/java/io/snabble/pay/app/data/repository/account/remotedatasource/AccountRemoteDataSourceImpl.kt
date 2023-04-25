package io.snabble.pay.app.data.repository.account.remotedatasource

import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.core.util.Result
import io.snabble.pay.sdk.SnabblePay
import io.snabble.pay.shared.account.domain.model.Account
import javax.inject.Inject

class AccountRemoteDataSourceImpl @Inject constructor(
    private val snabblePay: SnabblePay,
) : AccountRemoteDataSource {

    override suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck> =
        snabblePay.addNewAccount(
            appUri = appUri,
            city = city,
            twoLetterIsoCountryCode = twoLetterIsoCountryCode
        )

    override suspend fun deleteAccount(id: String): Result<Account> =
        snabblePay.removeAccount(id)

    override suspend fun getAllAccounts(): Result<List<Account>> =
        snabblePay.getAccounts()

    override suspend fun getAccount(id: String): Result<Account> =
        snabblePay.getAccount(id)
}
