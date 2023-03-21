package io.snabble.pay.mandate.domain.repository

import io.snabble.pay.core.util.Result
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateResponse

interface MandateRepository {

    suspend fun createMandate(accountId: String): Result<Mandate>

    suspend fun getMandate(accountId: String): Result<Mandate?>

    suspend fun respondToMandate(
        accountId: String,
        mandateId: String,
        response: MandateResponse,
    ): Result<Mandate>
}
