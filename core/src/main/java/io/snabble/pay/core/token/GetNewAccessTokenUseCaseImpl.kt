package io.snabble.pay.core.token

import io.snabble.pay.network.okhttp.authenticator.GetNewAccessTokenUseCase
import io.snabble.pay.network.okhttp.interceptor.AccessToken

internal class GetNewAccessTokenUseCaseImpl(
    private val tokenRepository: TokenRepository
) : GetNewAccessTokenUseCase {

    override suspend fun invoke(): AccessToken? =
        tokenRepository.getNewToken()?.accessToken
}
