package io.snabble.pay.app.domain.session.usecase

import android.util.Log
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.domain.session.SessionModel
import javax.inject.Inject

interface GetCurrentSessionUseCase {

    suspend operator fun invoke(accountId: String): AppResult<SessionModel>
}

class GetCurrentSessionUseCaseImpl @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
) : GetCurrentSessionUseCase {

    override suspend operator fun invoke(accountId: String): AppResult<SessionModel> {
        Log.d("xx", "invoke: ${getSessionsUseCase()}")
        return when (val result = getSessionsUseCase()) {
            is AppError -> AppError(result.value)
            is AppSuccess -> {
                if (result.value.isNotEmpty()) {
                    AppSuccess(result.value.first())
                } else {
                    createSessionUseCase(accountId)
                }
            }
        }
    }
}
