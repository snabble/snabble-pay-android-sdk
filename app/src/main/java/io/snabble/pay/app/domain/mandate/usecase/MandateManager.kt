package io.snabble.pay.app.domain.mandate.usecase

import io.snabble.pay.mandate.domain.model.Mandate
import javax.inject.Inject

interface MandateManager {

    suspend fun acceptMandate(accountId: String, mandateId: String): Result<Mandate>
    suspend fun createMandate(accountId: String): Result<Mandate>
    suspend fun getMandate(accountId: String): Result<Mandate?>
}

class MandateManagerImpl @Inject constructor(
    private val acceptMandateUseCase: AcceptMandateUseCase,
    private val createMandateUseCase: CreateMandateUseCase,
    private val getMandateUseCase: GetMandateUseCase,
) : MandateManager {

    override suspend fun acceptMandate(accountId: String, mandateId: String): Result<Mandate> =
        acceptMandateUseCase(accountId, mandateId)

    override suspend fun createMandate(accountId: String): Result<Mandate> =
        createMandateUseCase(accountId)

    override suspend fun getMandate(accountId: String): Result<Mandate?> =
        getMandateUseCase(accountId)
}
