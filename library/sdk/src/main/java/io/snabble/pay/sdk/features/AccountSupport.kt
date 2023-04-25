package io.snabble.pay.sdk.features

import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.account.domain.usecase.CreateAccountCheckUseCase
import io.snabble.pay.account.domain.usecase.GetAllAccountsUseCase
import io.snabble.pay.account.domain.usecase.GetSpecificAccountUseCase
import io.snabble.pay.account.domain.usecase.RemoveAccountUseCase
import io.snabble.pay.core.util.Result
import io.snabble.pay.shared.account.domain.model.Account

/**
 * Interface with features related to the [Account], like adding or removing a customer's bank
 * account.
 *
 * @since 1.0.0
 */
interface AccountSupport {

    /**
     * Creates an [AccountCheck] instance with a new validation link to add a bank account.
     *
     * @param appUri URI called by the backend after the account verification
     * @param city Customer's city, e.g. _Bonn_
     * @param twoLetterIsoCountryCode Customer's country code in two-letter format, e.g. _DE_<br/>
     * See here a
     * [list of supported values](https://docs.payone.com/pages/releaseview.action?pageId=1213959).
     *
     * @return Returns [Success](io.snabble.pay.core.util.Success) with an [AccountCheck] containing
     * the validation link to add a new account and the given [appUri], or a
     * [Failure](io.snabble.pay.core.util.Failure) otherwise.
     *
     * @since 1.0.0
     */
    suspend fun addNewAccount(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    /**
     * Get a specific bank account by its id.
     *
     * @param id ID of the Account
     *
     * @return Returns [Success](io.snabble.pay.core.util.Success) with an [Account],
     * [Failure](io.snabble.pay.core.util.Failure) otherwise.
     *
     * @since 1.0.0
     */
    suspend fun getAccount(id: String): Result<Account>

    /**
     * Get all accounts associated to the customer.
     *
     * @return Returns [Success](io.snabble.pay.core.util.Success) with a list of [Account]s that's
     * empty if the user hasn't added any bank accounts yet,
     * or a [Failure](io.snabble.pay.core.util.Failure) otherwise.
     *
     * @since 1.0.0
     */
    suspend fun getAccounts(): Result<List<Account>>

    /**
     * Removes the bank account from the customer's added accounts.
     *
     * @param id ID of the Account that should be removed
     *
     * @return Returns [Success](io.snabble.pay.core.util.Success) with the [Account] that's been
     * removed, [Failure](io.snabble.pay.core.util.Failure) otherwise.
     *
     * @since 1.0.0
     */
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
