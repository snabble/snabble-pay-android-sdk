package io.snabble.pay.session.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.session.data.dto.SessionDto
import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.shared.account.data.mapper.AccountMapper

internal class SessionMapper(
    private val accountMapper: AccountMapper,
    private val tokenMapper: TokenMapper,
    private val transactionMapper: TransactionMapper,
) : Mapper<SessionDto, Session> {

    override fun map(from: SessionDto): Session = with(from) {
        Session(
            createdAt = createdAt,
            id = id,
            account = accountMapper.map(accountDto),
            token = tokenMapper.map(token),
            transaction = transaction?.let(transactionMapper::map)
        )
    }
}
