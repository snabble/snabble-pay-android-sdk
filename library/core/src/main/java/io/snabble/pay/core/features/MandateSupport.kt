package io.snabble.pay.core.features

import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateResponse
import io.snabble.pay.mandate.domain.usecase.CreateMandateUseCase
import io.snabble.pay.mandate.domain.usecase.GetMandateUseCase
import io.snabble.pay.mandate.domain.usecase.RespondToMandateUseCase

interface MandateSupport {

    suspend fun createMandate(accountId: String): Result<Mandate>

    suspend fun getMandate(accountId: String): Result<Mandate?>

    suspend fun acceptMandate(accountId: String, mandateId: String): Result<Mandate>

    suspend fun declineMandate(accountId: String, mandateId: String): Result<Mandate>
}

class MandateSupportImpl(
    private val requestMandate: CreateMandateUseCase,
    private val getMandateState: GetMandateUseCase,
    private val sendMandateResponse: RespondToMandateUseCase,
) : MandateSupport {

    override suspend fun createMandate(accountId: String): Result<Mandate> =
        requestMandate(accountId = accountId)

    override suspend fun getMandate(accountId: String): Result<Mandate?> =
        getMandateState(accountId = accountId)

    override suspend fun acceptMandate(accountId: String, mandateId: String): Result<Mandate> =
        sendMandateResponse(
            accountId = accountId,
            mandateId = mandateId,
            response = MandateResponse.ACCEPTED
        )

    override suspend fun declineMandate(accountId: String, mandateId: String): Result<Mandate> =
        sendMandateResponse(
            accountId = accountId,
            mandateId = mandateId,
            response = MandateResponse.DECLINED
        )
}
