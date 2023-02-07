package io.snabble.pay.core.appcredentials.data.source.remote

import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.toAppCredentials
import io.snabble.pay.network.retrofit.Error
import io.snabble.pay.network.retrofit.Success
import io.snabble.pay.network.retrofit.SuccessNoContent
import io.snabble.pay.network.service.register.AppRegistrationService

class RemoteAppCredentialsDataSourceImpl(
    private val appRegistrationService: AppRegistrationService
) : RemoteAppCredentialsDataSource {

    override suspend fun fetchAppCredentials(): AppCredentials? {
        val credentials = when (val response = appRegistrationService
            .getAppCredentials()) {
            is Success -> response.data.toAppCredentials()
            is SuccessNoContent -> null
            is Error -> null
        }
        return credentials
    }
}
