package io.snabble.pay.sdk.internal.appcredentials.data.source.remote

import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.retrofit.SuccessNoContent
import io.snabble.pay.api.service.register.AppRegistrationService
import io.snabble.pay.api.service.register.dto.AppCredentialsDto
import io.snabble.pay.sdk.internal.appcredentials.data.mapper.AppCredentialsMapper
import io.snabble.pay.sdk.internal.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppCredentials

internal class RemoteAppCredentialsDataSourceImpl(
    private val appRegistrationService: AppRegistrationService,
    private val customerKey: CustomerKey,
    private val appCredentialsMapper: AppCredentialsMapper,
) : RemoteAppCredentialsDataSource {

    override suspend fun fetchAppCredentials(): AppCredentials? {
        val credentials = when (
            val response: ApiResponse<AppCredentialsDto> =
                appRegistrationService.getAppCredentials(customerKey.value)
        ) {
            is Success -> appCredentialsMapper.map(response.data)
            is SuccessNoContent -> null
            is ApiError -> null
        }
        return credentials
    }
}
