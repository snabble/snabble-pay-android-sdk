package io.snabble.pay.session.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.session.data.dto.TransactionDto
import io.snabble.pay.session.domain.model.Transaction

internal class TransactionMapper(
    private val stateMapper: TransactionStateMapper,
) : Mapper<TransactionDto, Transaction> {

    override fun map(from: TransactionDto): Transaction = with(from) {
        Transaction(
            amount = from.amount,
            currency = from.currency,
            id = from.id,
            state = stateMapper.map(state)
        )
    }
}
