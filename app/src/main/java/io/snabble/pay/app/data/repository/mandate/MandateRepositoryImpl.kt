package io.snabble.pay.app.data.repository.mandate

import io.snabble.pay.app.data.repository.mandate.remotedatasource.MandateRemoteDataSource
import io.snabble.pay.app.domain.mandate.MandateRepository
import javax.inject.Inject

class MandateRepositoryImpl @Inject constructor(
    private val remoteDataSource: MandateRemoteDataSource,
) : MandateRepository {

    override suspend fun createMandate(accountId: String) =
        remoteDataSource.createMandate(accountId)

    override suspend fun getMandate(accountId: String) =
        remoteDataSource.getMandate(accountId)

    override suspend fun acceptMandate(accountId: String, mandateId: String) =
        remoteDataSource.acceptMandate(accountId, mandateId)

    override suspend fun declineMandate(accountId: String, mandateId: String) =
        remoteDataSource.declineMandate(accountId, mandateId)
}
