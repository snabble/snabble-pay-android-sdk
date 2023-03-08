package io.snabble.pay.session.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.session.data.dto.SessionDto
import io.snabble.pay.session.domain.model.Session

internal class SessionMapper(
    private val tokenMapper: TokenMapper,
    private val transactionMapper: TransactionMapper,
) : Mapper<SessionDto, Session> {

    override fun map(from: SessionDto): Session = with(from) {
        Session(
            createdAt = createdAt,
            id = id,
            token = tokenMapper.map(token),
            transaction = transaction?.let(transactionMapper::map)
        )
    }
}
