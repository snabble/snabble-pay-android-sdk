package io.snabble.pay.app.domain.session

import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken

interface SessionRepository {

    suspend fun createSession(accountId: String): AppResult<Session>

    suspend fun deleteSession(id: String): AppResult<Session>

    suspend fun getSession(id: String): AppResult<Session>

    suspend fun getSessions(): AppResult<List<Session>>

    suspend fun updateToken(sessionId: String): AppResult<SessionToken>
}
