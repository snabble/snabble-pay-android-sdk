package io.snabble.pay.mandate.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateResponse

/** @suppress Dokka */
fun interface RespondToMandateUseCase {

    suspend operator fun invoke(
        accountId: String,
        mandateId: String,
        response: MandateResponse,
    ): Result<Mandate>
}
