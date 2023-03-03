package io.snabble.pay.core.features

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.account.domain.usecase.CreateAccountCheckUseCase
import io.snabble.pay.account.domain.usecase.GetAllAccountsUseCase
import io.snabble.pay.account.domain.usecase.GetSpecificAccountUseCase

interface AccountSupport {

    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAccount(id: String): Result<Account>

    suspend fun getAccounts(): Result<List<Account>>
}

class AccountSupportImpl(
    private val getAccountCheck: CreateAccountCheckUseCase,
    private val getAllAccounts: GetAllAccountsUseCase,
    private val getSpecificAccount: GetSpecificAccountUseCase,
) : AccountSupport {

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
