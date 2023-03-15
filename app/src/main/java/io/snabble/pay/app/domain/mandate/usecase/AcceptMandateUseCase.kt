package io.snabble.pay.app.domain.mandate.usecase

import io.snabble.pay.mandate.domain.model.Mandate

fun interface AcceptMandateUseCase {

    suspend operator fun invoke(accountId: String, mandateId: String): Result<Mandate>
}
