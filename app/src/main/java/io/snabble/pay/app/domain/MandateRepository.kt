package io.snabble.pay.app.domain

import io.snabble.pay.mandate.domain.model.Mandate

interface MandateRepository {

    suspend fun createMandate(accountId: String): Result<Mandate>

    suspend fun getMandate(accountId: String): Result<Mandate?>

    suspend fun acceptMandate(accountId: String, mandateId: String): Result<Mandate>

    suspend fun declineMandate(accountId: String, mandateId: String): Result<Mandate>
}
