package io.snabble.pay.core.appcredentials.data

import io.snabble.pay.core.SnabblePayConfiguration
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository

class AppCredentialsRepositoryImpl(
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
