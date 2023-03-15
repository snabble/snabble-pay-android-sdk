package io.snabble.pay.app.domain.mandate.usecase

import io.snabble.pay.mandate.domain.model.Mandate

fun interface GetMandateUseCase {

    suspend operator fun invoke(accountId: String): Result<Mandate?>
}
