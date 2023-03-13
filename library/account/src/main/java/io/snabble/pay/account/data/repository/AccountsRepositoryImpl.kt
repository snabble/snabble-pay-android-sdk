package io.snabble.pay.account.data.repository

import io.snabble.pay.account.data.mapper.AccountCheckMapper
import io.snabble.pay.account.data.mapper.AccountMapper
import io.snabble.pay.account.data.service.AccountService
import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.account.domain.repository.AccountsRepository
import io.snabble.pay.api.util.toResult

internal class AccountsRepositoryImpl(
    private val service: AccountService,
    private val accountMapper: AccountMapper,
    private val accountCheckMapper: AccountCheckMapper,
) : AccountsRepository {

    override suspend fun getAccount(id: String): Result<Account> = service
        .getAccount(id = id)
        .toResult(accountMapper::map)

    override suspend fun getAccountCheck(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck> = service
        .getAccountCheck(
            appUri = appUri,
            city = city,
            twoLetterIsoCountryCode = twoLetterIsoCountryCode
        )
        .toResult(accountCheckMapper::map)

    override suspend fun getAccounts(): Result<List<Account>> = service
        .getAccounts()
        .toResult { it.map(accountMapper::map) }

    override suspend fun removeAccount(id: String): Result<Account> = service
        .removeAccount(id = id)
        .toResult(accountMapper::map)
}
