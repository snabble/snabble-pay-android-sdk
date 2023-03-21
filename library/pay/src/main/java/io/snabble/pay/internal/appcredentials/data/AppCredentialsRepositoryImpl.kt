package io.snabble.pay.internal.appcredentials.data

import io.snabble.pay.SnabblePayConfiguration
import io.snabble.pay.internal.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.internal.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.internal.appcredentials.domain.repository.AppCredentialsRepository

internal class AppCredentialsRepositoryImpl(
    private val localDataSource: LocalAppCredentialsDataSource,
    private val remoteDataSource: RemoteAppCredentialsDataSource,
    private val config: SnabblePayConfiguration,
) : AppCredentialsRepository {

    override suspend fun getAppCredentials(): AppCredentials? =
        localDataSource.getAppCredentials()
            ?: remoteDataSource.fetchAppCredentials()
                ?.invokeNewAppCredentialsCallback()
                ?.apply { localDataSource.saveAppCredentials(this) }

    private fun AppCredentials.invokeNewAppCredentialsCallback(): AppCredentials {
        config.onNewAppCredentialsCallback?.onNewAppCredentials(
            appId = id.value,
            appSecret = secret.value
        )
        return this
    }
}
