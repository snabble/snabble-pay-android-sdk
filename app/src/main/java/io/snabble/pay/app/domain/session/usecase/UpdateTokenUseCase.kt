package io.snabble.pay.app.domain.session.usecase

import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.domain.session.SessionTokenModel

fun interface UpdateTokenUseCase {

    suspend operator fun invoke(sessionId: String): AppResult<SessionTokenModel>
}
