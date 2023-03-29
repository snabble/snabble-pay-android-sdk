package io.snabble.pay.account.data.mapper

import io.snabble.pay.account.data.dto.AccountDto
import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.api.util.Mapper

internal class AccountMapper(
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