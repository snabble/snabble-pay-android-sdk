package io.snabble.pay.core.domain.mapper

import io.snabble.pay.api.service.account.dto.AccountCheckDto
import io.snabble.pay.api.util.Mapper
import io.snabble.pay.core.domain.model.AccountCheck

class AccountCheckMapper : Mapper<AccountCheckDto, AccountCheck> {

    override fun map(from: AccountCheckDto): AccountCheck = AccountCheck(
        validationLink = from.validationLink,
        appUri = from.appUri
    )
}
