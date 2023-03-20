package io.snabble.pay.core.internal.token.data.source.remote

import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.retrofit.SuccessNoContent
import io.snabble.pay.api.service.register.AppRegistrationService
import io.snabble.pay.api.service.register.dto.TokenDto
import io.snabble.pay.core.internal.appcredentials.domain.GetAppCredentialsUseCase
import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.internal.token.data.mapper.TokenMapper
import io.snabble.pay.core.internal.token.data.source.RemoteTokenDataSource
import io.snabble.pay.core.internal.token.domain.model.Token

internal class RemoteTokenDataSourceImpl(
    private val getAppCredentials: GetAppCredentialsUseCase,
    private val registrationService: AppRegistrationService,
    private val tokenMapper: TokenMapper,
) : RemoteTokenDataSource {

    override suspend fun getToken(): Token? =
        getAppCredentials()
            ?.fetchNewAccessToken()
            ?.let(tokenMapper::map)

    private suspend fun AppCredentials.fetchNewAccessToken(): TokenDto? {
        val tokenResponse =
            registrationService.getToken(appIdentifier = id.value, appSecret = secret.value)
        return when (tokenResponse) {
            is Success -> tokenResponse.data
            is SuccessNoContent -> null
            is ApiError -> null
        }
    }
}
