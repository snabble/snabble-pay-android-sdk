package io.snabble.pay.app.data.repository.mandate.remotedatasource

import io.snabble.pay.mandate.domain.model.Mandate

interface MandateRemoteDataSource {

    suspend fun createMandate(accountId: String): Result<Mandate>

    suspend fun getMandate(accountId: String): Result<Mandate?>

    suspend fun acceptMandate(accountId: String, mandateId: String): Result<Mandate>

    suspend fun declineMandate(accountId: String, mandateId: String): Result<Mandate>
}
