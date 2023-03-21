package io.snabble.pay.session.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.session.domain.model.SessionToken

fun interface UpdateSessionTokenUseCase {

    suspend operator fun invoke(sessionId: String): Result<SessionToken>
}
