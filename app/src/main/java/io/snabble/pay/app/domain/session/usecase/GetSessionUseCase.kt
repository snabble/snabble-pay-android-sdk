package io.snabble.pay.app.domain.session.usecase

import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.domain.session.SessionModel

fun interface GetSessionUseCase {

    suspend operator fun invoke(id: String): AppResult<SessionModel>
}
