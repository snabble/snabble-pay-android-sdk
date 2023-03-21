package io.snabble.pay.session.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.session.domain.model.Session

fun interface GetSessionUseCase {

    suspend operator fun invoke(id: String): Result<Session>
}
