package io.snabble.pay.core.appcredentials.data.repository

import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.network.AppCredentials
import io.snabble.pay.network.AppCredentialsRepository

class AppCredentialsRepositoryImpl(
    private val localDataSource: LocalAppCredentialsDataSource,
    private val remoteDataSource: RemoteAppCredentialsDataSource,
) : AppCredentialsRepository {

    override suspend fun getAppCredentials(): AppCredentials? {
        val credentials = localDataSource.getAppCredentials()
        val newCredentials = when (credentials) {
            null -> remoteDataSource.fetchAppCredentials()
            else -> credentials
        }
        if (credentials != newCredentials && newCredentials != null) {
            localDataSource.saveAppCredentials(newCredentials)
        }
        return newCredentials
    }
}
