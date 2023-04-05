package io.snabble.pay.app.data.repository.session

import io.snabble.pay.SnabblePay
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.toErrorResponse
import io.snabble.pay.app.domain.session.SessionModel
import io.snabble.pay.app.domain.session.SessionRepository
import io.snabble.pay.app.domain.session.SessionTokenModel
import io.snabble.pay.core.util.Failure
import io.snabble.pay.core.util.Success
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val snabblePay: SnabblePay,
) : SessionRepository {

    override suspend fun createSession(accountId: String): AppResult<SessionModel> {
        return when (val result = snabblePay.createNewSession(accountId)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(SessionModel.from(result.value))
        }
    }

    override suspend fun deleteSession(id: String): AppResult<SessionModel> {
        return when (val result = snabblePay.deleteSession(id)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(SessionModel.from(result.value))
        }
    }

    override suspend fun getSession(id: String): AppResult<SessionModel> {
        return when (val result = snabblePay.getSession(id)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(SessionModel.from(result.value))
        }
    }

    override suspend fun getSessions(): AppResult<List<SessionModel>> {
        return when (val result = snabblePay.getAllSessions()) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(
                result.value.map { SessionModel.from(it) }
            )
        }
    }

    override suspend fun updateToken(sessionId: String): AppResult<SessionTokenModel> {
        return when (val result = snabblePay.updateSessionToken(sessionId)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(SessionTokenModel.from(result.value))
        }
    }
}
