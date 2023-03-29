package io.snabble.pay.session.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.session.domain.model.Session

/** @suppress Dokka */
fun interface GetAllSessionsUseCase {

    suspend operator fun invoke(): Result<List<Session>>
}
