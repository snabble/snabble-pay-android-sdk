package io.snabble.pay.core.token.datasource.remote

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.core.token.datasource.RemoteTokenDataSource
import io.snabble.pay.core.token.datasource.TokenDto
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.retrofit.Error
import io.snabble.pay.network.retrofit.Success
import io.snabble.pay.network.retrofit.SuccessNoContent
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

    private suspend fun AppCredentials.fetchNewAccessToken(): ApiTokenDto? {
        val tokenResponse =
            registrationService.getToken(appIdentifier = id.value, appSecret = secret.value)
        return when (tokenResponse) {
            is Success -> tokenResponse.data
            is SuccessNoContent -> null
            is Error -> null
        }
    }
}

private fun ApiTokenDto.asTokenDto() = TokenDto(
    accessToken = AccessToken("$tokenType $token"),
    expiryDate = expiryDate,
)
