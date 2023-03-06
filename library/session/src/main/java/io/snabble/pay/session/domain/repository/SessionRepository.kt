package io.snabble.pay.session.domain.repository

import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken

interface SessionRepository {

    suspend fun createSession(accountId: String): Result<Session>

    suspend fun deleteSession(id: String): Result<Session>

    suspend fun getSession(id: String): Result<Session>

    suspend fun getSessions(): Result<List<Session>>

    suspend fun updateToken(sessionId: String): Result<SessionToken>
}
