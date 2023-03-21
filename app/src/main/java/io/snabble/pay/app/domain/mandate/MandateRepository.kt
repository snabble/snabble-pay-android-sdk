package io.snabble.pay.app.domain.mandate

import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.mandate.domain.model.Mandate

interface MandateRepository {

    suspend fun createMandate(accountId: String): AppResult<Mandate>
    suspend fun acceptMandate(accountId: String, mandateId: String): AppResult<Mandate>
    suspend fun getMandate(accountId: String): AppResult<Mandate?>
}
