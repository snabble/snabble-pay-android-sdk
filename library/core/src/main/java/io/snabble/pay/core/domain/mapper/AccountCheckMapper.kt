package io.snabble.pay.core.domain.mapper

import io.snabble.pay.api.service.account.dto.AccountCheckDto
import io.snabble.pay.core.domain.model.AccountCheck
import io.snabble.pay.core.util.Mapper

class AccountCheckMapper : Mapper<AccountCheckDto, AccountCheck> {

    override fun map(from: AccountCheckDto): AccountCheck = AccountCheck(
        validationLink = from.validationLink,
        appUri = from.appUri
    )
}
