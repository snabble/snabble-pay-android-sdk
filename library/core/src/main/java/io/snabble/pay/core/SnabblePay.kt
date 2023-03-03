package io.snabble.pay.core

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.core.features.AccountSupport
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
    accountSupport: AccountSupport,
    mandateSupport: MandateSupport,
) : SnabblePay,
    AccountSupport by accountSupport,
    MandateSupport by mandateSupport
