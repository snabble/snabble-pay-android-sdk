package io.snabble.pay.app.data.repository.mandate.remotedatasource

import io.snabble.pay.core.SnabblePay
import javax.inject.Inject

class MandateRemoteDataSourceImpl @Inject constructor(
    private val snabblePay: SnabblePay,
) : MandateRemoteDataSource {

    override suspend fun createMandate(accountId: String) =
        snabblePay.createMandate(accountId)

    override suspend fun getMandate(accountId: String) =
        snabblePay.getMandate(accountId)

    override suspend fun acceptMandate(accountId: String, mandateId: String) =
        snabblePay.acceptMandate(accountId, mandateId)

    override suspend fun declineMandate(accountId: String, mandateId: String) =
        snabblePay.declineMandate(accountId, mandateId)
}
