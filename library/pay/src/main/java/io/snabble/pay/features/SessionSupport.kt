package io.snabble.pay.features

import io.snabble.pay.core.util.Result
import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken
import io.snabble.pay.session.domain.usecase.CreateSessionUseCase
import io.snabble.pay.session.domain.usecase.DeleteSessionUseCase
import io.snabble.pay.session.domain.usecase.GetAllSessionsUseCase
import io.snabble.pay.session.domain.usecase.GetSessionUseCase
import io.snabble.pay.session.domain.usecase.UpdateSessionTokenUseCase

/**
 * Interface with features related to the session, providing functions
 * to create, delete or fetch sessions, including the feature to update a [SessionToken].
 */
interface SessionSupport {

    /**
     * Creates a session for the specific account.
     *
     * Returns the [Session] without a [Transaction](io.snabble.pay.session.domain.model.Transaction).
     *
     * @param accountId ID for the account associated with the session
     *
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing the [Session]
     * created for the given account, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun createNewSession(accountId: String): Result<Session>

    /**
     * Deletes a specific session.
     *
     * @param id ID of the session to be deleted
     *
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing the [Session]
     * that has been deleted, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun deleteSession(id: String): Result<Session>

    /**
     * Fetch all sessions available for the current application.
     *
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing a list of [Session]`s
     * available, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun getAllSessions(): Result<List<Session>>

    /**
     * Fetch a specific session.
     *
     * @param id ID for the specific session to fetch
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing the specific [Session],
     * or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun getSession(id: String): Result<Session>

    /**
     * Update`s the session token associated to a specific session.
     *
     * Should be used then a [SessionToken] can be refreshed. Returns a new session token
     * associated to the given session id.
     *
     * @param sessionId ID for session associated to the session token
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing a new [SessionToken]
     * for the given session, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun updateSessionToken(sessionId: String): Result<SessionToken>
}

internal class SessionSupportImpl(
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
