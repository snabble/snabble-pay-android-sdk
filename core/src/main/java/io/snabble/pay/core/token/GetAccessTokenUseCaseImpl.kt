package io.snabble.pay.core.token

import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.okhttp.interceptor.GetAccessTokenUseCase

internal class GetAccessTokenUseCaseImpl(
    private val tokenRepository: TokenRepository
) : GetAccessTokenUseCase {

    override suspend fun getAccessToken(): AccessToken? =
        tokenRepository.getToken()?.accessToken
}
