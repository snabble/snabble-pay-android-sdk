package io.snabble.pay.core.internal.token.domain

import io.snabble.pay.core.internal.token.domain.repository.TokenRepository
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.okhttp.interceptor.GetAccessTokenUseCase

internal class GetAccessTokenUseCaseImpl(
    private val tokenRepository: TokenRepository,
) : GetAccessTokenUseCase {

    override suspend fun invoke(): AccessToken? =
        tokenRepository.getToken()?.accessToken
}
