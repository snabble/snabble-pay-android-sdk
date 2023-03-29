package io.snabble.pay.features

import io.snabble.pay.core.util.Result
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateResponse
import io.snabble.pay.mandate.domain.usecase.CreateMandateUseCase
import io.snabble.pay.mandate.domain.usecase.GetMandateUseCase
import io.snabble.pay.mandate.domain.usecase.RespondToMandateUseCase


/**
 * Interface with features related to the mandate, providing functions
 * to create, accept or deny a given mandate.
 */
interface MandateSupport {

    /**
     * Creates a mandate for the specific account.
     *
     * Returns the [Mandate] containing the mandate id, the mandate state [PENDING](io.snabble.pay.mandate.domain.model.MandateState.PENDING)
     * and the html text to be displayed.
     *
     * @param accountId ID for the account associated with the mandate
     *
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing the [Mandate]
     * created for the given account, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun createMandate(accountId: String): Result<Mandate>

    /**
     * Fetch the mandate for a specific account.
     *
     * @param accountId ID for the account associated with the mandate
     *
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing the [Mandate]
     * associated with the given account, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun getMandate(accountId: String): Result<Mandate?>

    /**
     * Accepts a specific mandate.
     *
     * Returns the [Mandate] containing the mandate id, the mandate state [ACCEPTED](io.snabble.pay.mandate.domain.model.MandateState.ACCEPTED)
     * without the html text.
     *
     * @param accountId ID for the account associated with the mandate
     * @param mandateId ID for the mandate to be accepted
     *
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing the [Mandate]
     * that has been accepted, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun acceptMandate(accountId: String, mandateId: String): Result<Mandate>

    /**
     * Declines a specific mandate.
     *
     * Returns the [Mandate] containing the mandate id, the mandate state [DECLINED](io.snabble.pay.mandate.domain.model.MandateState.DECLINED)
     * without the html text.
     *
     * @param accountId ID for the account associated with the mandate
     * @param mandateId ID for the mandate to be accepted
     *
     * @return
     * Returns [Success](io.snabble.pay.core.util.Success) containing the [Mandate]
     * that has been declined, or [Failure](io.snabble.pay.core.util.Failure) otherwise.
     */
    suspend fun declineMandate(accountId: String, mandateId: String): Result<Mandate>
}

internal class MandateSupportImpl(
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
