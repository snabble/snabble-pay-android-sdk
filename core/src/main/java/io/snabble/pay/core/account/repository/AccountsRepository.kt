package io.snabble.pay.core.account.repository

import io.snabble.pay.api.service.account.AccountService
import io.snabble.pay.api.service.account.dto.AccountCheckDto
import io.snabble.pay.api.service.account.dto.AccountDto
import io.snabble.pay.core.domain.model.Account
import io.snabble.pay.core.domain.model.AccountCheck
import io.snabble.pay.core.toResult
import io.snabble.pay.core.util.Mapper

interface AccountsRepository {

    suspend fun getAccountCheck(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>

    suspend fun getAccount(id: String): Result<Account>

    suspend fun getAccounts(): Result<List<Account>>
}

class AccountsRepositoryImpl(
    private val accountService: AccountService,
    private val accountMapper: Mapper<AccountDto, Account>,
    private val accountCheckMapper: Mapper<AccountCheckDto, AccountCheck>,
) : AccountsRepository {

    override suspend fun getAccount(id: String): Result<Account> = accountService
        .getAccount(id)
        .toResult(accountMapper::map)

    override suspend fun getAccountCheck(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck> = accountService
        .getAccountCheck(appUri, city, twoLetterIsoCountryCode)
        .toResult(accountCheckMapper::map)

    override suspend fun getAccounts(): Result<List<Account>> = accountService
        .getAccounts()
        .toResult { it.map(accountMapper::map) }
}