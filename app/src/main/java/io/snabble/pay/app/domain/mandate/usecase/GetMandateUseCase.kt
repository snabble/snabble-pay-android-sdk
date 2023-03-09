package io.snabble.pay.app.domain.mandate.usecase

import io.snabble.pay.app.domain.mandate.MandateRepository
import io.snabble.pay.mandate.domain.model.Mandate
import javax.inject.Inject

interface GetMandateUseCase {

    suspend operator fun invoke(accountId: String): Result<Mandate?>
}

class GetMandateUseCaseImpl @Inject constructor(
    private val repository: MandateRepository,
) : GetMandateUseCase {

    override suspend operator fun invoke(accountId: String): Result<Mandate?> =
        repository.getMandate(accountId)
}
