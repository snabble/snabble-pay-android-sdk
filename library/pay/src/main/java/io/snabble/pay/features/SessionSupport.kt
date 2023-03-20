package io.snabble.pay.features

import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken
import io.snabble.pay.session.domain.usecase.CreateSessionUseCase
import io.snabble.pay.session.domain.usecase.DeleteSessionUseCase
import io.snabble.pay.session.domain.usecase.GetAllSessionsUseCase
import io.snabble.pay.session.domain.usecase.GetSessionUseCase
import io.snabble.pay.session.domain.usecase.UpdateSessionTokenUseCase

interface SessionSupport {

    suspend fun createNewSession(accountId: String): Result<Session>

    suspend fun deleteSession(id: String): Result<Session>

    suspend fun getAllSessions(): Result<List<Session>>

    suspend fun getSession(id: String): Result<Session>

    suspend fun updateSessionToken(sessionId: String): Result<SessionToken>
}

class SessionSupportImpl(
    private val createSession: CreateSessionUseCase,
    private val deleteSpecificSession: DeleteSessionUseCase,
    private val getSessions: GetAllSessionsUseCase,
    private val getSpecificSession: GetSessionUseCase,
    private val updateToken: UpdateSessionTokenUseCase,
) : SessionSupport {

    override suspend fun createNewSession(
        accountId: String,
    ): Result<Session> = createSession(accountId)

    override suspend fun deleteSession(
        id: String,
    ): Result<Session> = deleteSpecificSession(id = id)

    override suspend fun getAllSessions(): Result<List<Session>> = getSessions()

    override suspend fun getSession(
        id: String,
    ): Result<Session> = getSpecificSession(id = id)

    override suspend fun updateSessionToken(
        sessionId: String,
    ): Result<SessionToken> = updateToken(sessionId = sessionId)
}
