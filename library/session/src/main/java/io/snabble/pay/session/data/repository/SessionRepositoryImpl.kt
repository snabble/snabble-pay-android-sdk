package io.snabble.pay.session.data.repository

import io.snabble.pay.api.util.toResult
import io.snabble.pay.core.util.Result
import io.snabble.pay.session.data.dto.AccountIdDto
import io.snabble.pay.session.data.mapper.SessionMapper
import io.snabble.pay.session.data.mapper.TokenMapper
import io.snabble.pay.session.data.service.SessionService
import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken
import io.snabble.pay.session.domain.repository.SessionRepository

internal class SessionRepositoryImpl(
    private val service: SessionService,
    private val sessionMapper: SessionMapper,
    private val tokenMapper: TokenMapper,
) : SessionRepository {

    override suspend fun createSession(accountId: String): Result<Session> = service
        .createSession(accountId = AccountIdDto(id = accountId))
        .toResult(sessionMapper::map)

    override suspend fun deleteSession(id: String): Result<Session> = service
        .deleteSession(sessionId = id)
        .toResult(sessionMapper::map)

    override suspend fun getSession(id: String): Result<Session> = service
        .getSession(sessionId = id)
        .toResult(sessionMapper::map)

    override suspend fun getSessions(): Result<List<Session>> = service
        .getSessions()
        .toResult { it.map(sessionMapper::map) }

    override suspend fun updateToken(sessionId: String): Result<SessionToken> = service
        .updateToken(sessionId = sessionId)
        .toResult(tokenMapper::map)
}
