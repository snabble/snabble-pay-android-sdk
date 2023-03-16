package io.snabble.pay.session.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.session.data.dto.TransactionStateDto
import io.snabble.pay.session.domain.model.TransactionState

internal class TransactionStateMapper : Mapper<TransactionStateDto, TransactionState> {

    override fun map(from: TransactionStateDto): TransactionState = when (from) {
        TransactionStateDto.ABORTED -> TransactionState.ABORTED
        TransactionStateDto.ERRORED -> TransactionState.ERRORED
        TransactionStateDto.FAILED -> TransactionState.FAILED
        TransactionStateDto.PENDING -> TransactionState.PENDING
        TransactionStateDto.PREAUTHORIZED -> TransactionState.PREAUTHORIZED
        TransactionStateDto.PREAUTH_FAILED -> TransactionState.PREAUTH_FAILED
        TransactionStateDto.SUCCESSFUL -> TransactionState.SUCCESSFUL
    }
}
