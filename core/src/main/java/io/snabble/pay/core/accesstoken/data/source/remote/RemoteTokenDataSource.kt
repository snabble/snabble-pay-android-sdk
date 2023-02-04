package io.snabble.pay.core.accesstoken.data.source.remote

import io.snabble.pay.core.accesstoken.data.source.dto.TokenDto
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.service.register.AppRegistrationService
import io.snabble.pay.network.service.register.dto.TokenDto as ApiTokenDto

interface RemoteTokenDataSource {

    suspend fun getToken(): TokenDto?
}

class RemoteTokenDataSourceImpl(
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
                appIdentifier = id.id,
                appSecret = secret.secret,
            )
            .execute()
            .body()
}

private fun ApiTokenDto.asTokenDto() = TokenDto(
    accessToken = AccessToken("$tokenType $token"),
    expiryDate = expiryDate
)
