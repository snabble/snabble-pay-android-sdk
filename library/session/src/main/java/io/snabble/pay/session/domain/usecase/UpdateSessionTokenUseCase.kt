package io.snabble.pay.session.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.session.domain.model.SessionToken

/** @suppress Dokka */
fun interface UpdateSessionTokenUseCase {

    suspend operator fun invoke(sessionId: String): Result<SessionToken>
}
