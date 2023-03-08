package io.snabble.pay.session.domain.usecase

import io.snabble.pay.session.domain.model.Session

fun interface CreateSessionUseCase {

    suspend operator fun invoke(accountId: String): Result<Session>
}
