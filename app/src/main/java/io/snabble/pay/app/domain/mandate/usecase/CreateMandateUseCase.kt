package io.snabble.pay.app.domain.mandate.usecase

import io.snabble.pay.app.domain.mandate.MandateRepository
import io.snabble.pay.mandate.domain.model.Mandate
import javax.inject.Inject

interface CreateMandateUseCase {

    suspend operator fun invoke(accountId: String): Result<Mandate>
}

class CreateMandateUseCaseImpl @Inject constructor(
    private val repository: MandateRepository,
) : CreateMandateUseCase {

    override suspend operator fun invoke(accountId: String): Result<Mandate> =
        repository.createMandate(accountId)
}
