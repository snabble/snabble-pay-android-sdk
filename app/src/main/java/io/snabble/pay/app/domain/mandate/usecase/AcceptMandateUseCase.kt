package io.snabble.pay.app.domain.mandate.usecase

import io.snabble.pay.app.domain.mandate.MandateRepository
import io.snabble.pay.mandate.domain.model.Mandate
import javax.inject.Inject

interface AcceptMandateUseCase {

    suspend operator fun invoke(accountId: String, mandateId: String): Result<Mandate>
}

class AcceptMandateUseCaseImpl @Inject constructor(
    private val repository: MandateRepository,
) : AcceptMandateUseCase {

    override suspend operator fun invoke(accountId: String, mandateId: String): Result<Mandate> =
        repository.acceptMandate(accountId, mandateId)
}
