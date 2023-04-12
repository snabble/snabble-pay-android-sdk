package io.snabble.pay.mandate.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.mandate.domain.model.Mandate

/** @suppress Dokka */
fun interface CreateMandateUseCase {

    suspend operator fun invoke(accountId: String): Result<Mandate>
}
