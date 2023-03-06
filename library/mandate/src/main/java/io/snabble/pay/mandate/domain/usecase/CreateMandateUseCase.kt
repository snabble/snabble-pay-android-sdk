package io.snabble.pay.mandate.domain.usecase

import io.snabble.pay.mandate.domain.model.Mandate

fun interface CreateMandateUseCase {

    suspend operator fun invoke(accountId: String): Result<Mandate>
}
