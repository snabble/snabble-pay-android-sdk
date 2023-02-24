package io.snabble.pay.core.token.datasource.remote

import io.snabble.pay.api.retrofit.Error
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.retrofit.SuccessNoContent
import io.snabble.pay.api.service.register.AppRegistrationService
import io.snabble.pay.api.service.register.dto.TokenDto
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.core.token.datasource.RemoteTokenDataSource
import io.snabble.pay.core.token.datasource.Token
import io.snabble.pay.network.okhttp.interceptor.AccessToken

internal class RemoteTokenDataSourceImpl(
    private val appCredentialsRepository: AppCredentialsRepository,
    private val registrationService: AppRegistrationService,
) : RemoteTokenDataSource {

    override suspend fun getToken(): Token? =
        appCredentialsRepository.getAppCredentials()
            ?.fetchNewAccessToken()
            ?.asToken()

    private suspend fun AppCredentials.fetchNewAccessToken(): TokenDto? {
        val tokenResponse =
            registrationService.getToken(appIdentifier = id.value, appSecret = secret.value)
        return when (tokenResponse) {
            is Success -> tokenResponse.data
            is SuccessNoContent -> null
            is Error -> null
        }
    }
}

private fun TokenDto.asToken() = Token(
    accessToken = AccessToken("$tokenType $token"),
    expiryDate = expiryDate
)
