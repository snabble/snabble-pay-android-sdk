package io.snabble.pay.session.domain.usecase

import io.snabble.pay.session.domain.model.Session

fun interface GetAllSessionsUseCase {

    suspend operator fun invoke(): Result<List<Session>>
}
