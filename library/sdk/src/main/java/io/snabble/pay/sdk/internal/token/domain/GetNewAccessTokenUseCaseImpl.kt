package io.snabble.pay.sdk.internal.token.domain

import io.snabble.pay.network.okhttp.authenticator.GetNewAccessTokenUseCase
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.sdk.internal.token.domain.repository.TokenRepository

internal class GetNewAccessTokenUseCaseImpl(
    private val tokenRepository: TokenRepository,
) : GetNewAccessTokenUseCase {

    override suspend fun invoke(): AccessToken? =
        tokenRepository.getNewToken()?.accessToken
}
