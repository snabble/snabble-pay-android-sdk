package io.snabble.pay.core.accesstoken.datasource.remote

import io.snabble.pay.core.accesstoken.datasource.RemoteTokenDataSource
import io.snabble.pay.core.accesstoken.datasource.TokenDto
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.service.register.AppRegistrationService
import io.snabble.pay.network.service.register.dto.TokenDto as ApiTokenDto

internal class RemoteTokenDataSourceImpl(
    private val appCredentialsRepository: AppCredentialsRepository,
    private val registrationService: AppRegistrationService,
) : RemoteTokenDataSource {

    override suspend fun getToken(): TokenDto? =
        appCredentialsRepository.getAppCredentials()
            ?.fetchNewAccessToken()
            ?.asTokenDto()

    private fun AppCredentials.fetchNewAccessToken(): ApiTokenDto? =
        registrationService
            .getToken(
                appIdentifier = id.value,
                appSecret = secret.value,
            )
            .execute()
            .body()
}

private fun ApiTokenDto.asTokenDto() = TokenDto(
    accessToken = AccessToken("$tokenType $token"),
    expiryDate = expiryDate
)
