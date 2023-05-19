package io.snabble.pay.app.domain.session.usecase

import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.domain.session.SessionModel
import io.snabble.pay.core.Reason
import javax.inject.Inject

interface GetCurrentSessionUseCase {

    suspend operator fun invoke(accountId: String): AppResult<SessionModel>
}

class GetCurrentSessionUseCaseImpl @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
) : GetCurrentSessionUseCase {

    override suspend operator fun invoke(accountId: String): AppResult<SessionModel> {
        return when (val result = getSessionsUseCase()) {
            is AppError -> if (result.value?.reason == Reason.INVALID_SESSION_STATE) {
                // New session since the current session is already used
                createSessionUseCase(accountId)
            } else {
                AppError(result.value)
            }

            is AppSuccess -> {
                val sessionModel = result.value.find { it.accountCard.accountId == accountId }
                if (sessionModel != null) {
                    AppSuccess(sessionModel)
                } else {
                    createSessionUseCase(accountId)
                }
            }
        }
    }
}
