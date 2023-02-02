package io.snabble.pay.core.appcredentials.data.source.remote

import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.toAppCredentials
import io.snabble.pay.network.service.register.AppRegistrationService

class RemoteAppCredentialsDataSourceImpl(
    private val appRegistrationService: AppRegistrationService
) : RemoteAppCredentialsDataSource {

    override suspend fun fetchAppCredentials(): AppCredentials? {
        val response = appRegistrationService.getAppCredentials().execute()
        val appCredentials = response.body()
        appCredentials ?: return null
        return appCredentials.toAppCredentials()
    }
}
