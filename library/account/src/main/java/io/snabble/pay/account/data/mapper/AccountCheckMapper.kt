package io.snabble.pay.account.data.mapper

import io.snabble.pay.account.data.dto.AccountCheckDto
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.api.util.Mapper

internal class AccountCheckMapper : Mapper<AccountCheckDto, AccountCheck> {

    override fun map(from: AccountCheckDto): AccountCheck = AccountCheck(
        validationLink = from.validationLink,
        appUri = from.appUri
    )
}
