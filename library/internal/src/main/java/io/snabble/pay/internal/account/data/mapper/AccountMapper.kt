package io.snabble.pay.internal.account.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.internal.account.data.dto.AccountDto
import io.snabble.pay.shared.account.domain.model.Account

class AccountMapper(
    private val mandateStateMapper: MandateStateMapper,
) : Mapper<AccountDto, Account> {

    override fun map(from: AccountDto): Account = Account(
        bank = from.bank,
        createdAt = from.createdAt,
        currencyCode = from.currencyCode,
        holderName = from.holderName,
        iban = from.iban,
        id = from.id,
        mandateState = mandateStateMapper.map(from.mandateState),
        name = from.name
    )
}
