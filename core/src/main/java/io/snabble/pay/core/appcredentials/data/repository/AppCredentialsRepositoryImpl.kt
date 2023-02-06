package io.snabble.pay.core.appcredentials.data.repository

import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository

class AppCredentialsRepositoryImpl(
    private val localDataSource: LocalAppCredentialsDataSource,
    private val remoteDataSource: RemoteAppCredentialsDataSource,
) : AppCredentialsRepository {

    override suspend fun getAppCredentials(): AppCredentials? =
        localDataSource.getAppCredentials() ?: remoteDataSource.fetchAppCredentials().data.apply {
            if (this != null) localDataSource.saveAppCredentials(this)
        }
}
