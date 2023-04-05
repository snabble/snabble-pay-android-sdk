package io.snabble.pay.app.data.repository.mandate

import io.snabble.pay.SnabblePay
import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.data.utils.toErrorResponse
import io.snabble.pay.app.domain.mandate.MandateRepository
import io.snabble.pay.core.util.Failure
import io.snabble.pay.core.util.Success
import io.snabble.pay.mandate.domain.model.Mandate
import javax.inject.Inject

class MandateRepositoryImpl @Inject constructor(
    private val snabblePay: SnabblePay,
) : MandateRepository {

    override suspend fun createMandate(accountId: String): AppResult<Mandate> {
        return when (val result = snabblePay.createMandate(accountId)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(result.value)
        }
    }

    override suspend fun acceptMandate(accountId: String, mandateId: String): AppResult<Mandate> {
        return when (val result = snabblePay.acceptMandate(accountId, mandateId)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(result.value)
        }
    }

    override suspend fun declineMandate(accountId: String, mandateId: String): AppResult<Mandate> {
        return when (val result = snabblePay.declineMandate(accountId, mandateId)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(result.value)
        }
    }

    override suspend fun getMandate(accountId: String): AppResult<Mandate?> {
        return when (val result = snabblePay.getMandate(accountId)) {
            is Failure -> AppError(result.error.toErrorResponse())
            is Success -> AppSuccess(result.value)
        }
    }
}
