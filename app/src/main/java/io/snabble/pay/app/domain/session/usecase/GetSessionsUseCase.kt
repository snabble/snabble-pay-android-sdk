package io.snabble.pay.app.domain.session.usecase

import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.domain.session.SessionModel

fun interface GetSessionsUseCase {

   suspend operator fun invoke(): AppResult<List<SessionModel>>

}

