package io.snabble.pay.core

import io.snabble.pay.core.account.CreateAccountCheckUseCase
import io.snabble.pay.core.account.GetAllAccountsUseCase
import io.snabble.pay.core.account.GetSpecificAccountUseCase
import io.snabble.pay.core.domain.model.Account
import io.snabble.pay.core.domain.model.AccountCheck
import io.snabble.pay.core.features.MandateSupport

interface SnabblePay : MandateSupport {

    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAccount(id: String): Result<Account>

    suspend fun getAccounts(): Result<List<Account>>
}

class SnabblePayImpl internal constructor(
    configuration: SnabblePayConfiguration,
    mandateSupport: MandateSupport,
) : SnabblePay,
    MandateSupport by mandateSupport {

    private val getAccountCheck: CreateAccountCheckUseCase by configuration.koin.inject()
    private val getAllAccounts: GetAllAccountsUseCase by configuration.koin.inject()
    private val getSpecificAccount: GetSpecificAccountUseCase by configuration.koin.inject()

    override suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck> =
        getAccountCheck(
            appUri = appUri,
            city = city,
            twoLetterIsoCountryCode = twoLetterIsoCountryCode
        )

    override suspend fun getAccount(id: String): Result<Account> =
        getSpecificAccount(id = id)

    override suspend fun getAccounts(): Result<List<Account>> =
        getAllAccounts()
}
