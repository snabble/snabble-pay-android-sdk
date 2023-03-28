package io.snabble.pay.features

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.account.domain.usecase.CreateAccountCheckUseCase
import io.snabble.pay.account.domain.usecase.GetAllAccountsUseCase
import io.snabble.pay.account.domain.usecase.GetSpecificAccountUseCase
import io.snabble.pay.account.domain.usecase.RemoveAccountUseCase
import io.snabble.pay.core.util.Result

/** @suppress Dokka */
interface AccountSupport {

    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAccount(id: String): Result<Account>

    suspend fun getAccounts(): Result<List<Account>>

    suspend fun removeAccount(id: String): Result<Account>
}

internal class AccountSupportImpl(
    private val getAccountCheck: CreateAccountCheckUseCase,
    private val getAllAccounts: GetAllAccountsUseCase,
    private val getSpecificAccount: GetSpecificAccountUseCase,
    private val removeSpecificAccount: RemoveAccountUseCase,
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

    override suspend fun removeAccount(id: String): Result<Account> =
        removeSpecificAccount(id = id)
}
