package io.snabble.pay.sdk.internal.token.domain

import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.okhttp.interceptor.GetAccessTokenUseCase
import io.snabble.pay.sdk.internal.token.domain.repository.TokenRepository

internal class GetAccessTokenUseCaseImpl(
    private val tokenRepository: TokenRepository,
) : GetAccessTokenUseCase {

    override suspend fun invoke(): AccessToken? =
        tokenRepository.getToken()?.accessToken
}
