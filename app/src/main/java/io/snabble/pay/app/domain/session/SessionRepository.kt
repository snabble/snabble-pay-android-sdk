package io.snabble.pay.app.domain.session

import io.snabble.pay.app.data.utils.AppResult

interface SessionRepository {

    suspend fun createSession(accountId: String): AppResult<SessionModel>

    suspend fun deleteSession(id: String): AppResult<SessionModel>

    suspend fun getSession(id: String): AppResult<SessionModel>

    suspend fun getSessions(): AppResult<List<SessionModel>>

    suspend fun updateToken(sessionId: String): AppResult<SessionTokenModel>
}
