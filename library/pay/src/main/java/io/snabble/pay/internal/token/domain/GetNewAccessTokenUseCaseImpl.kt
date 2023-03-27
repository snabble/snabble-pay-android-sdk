package io.snabble.pay.internal.token.domain

import io.snabble.pay.internal.token.domain.repository.TokenRepository
import io.snabble.pay.network.okhttp.authenticator.GetNewAccessTokenUseCase
import io.snabble.pay.network.okhttp.interceptor.AccessToken

internal class GetNewAccessTokenUseCaseImpl(
    private val tokenRepository: TokenRepository,
) : GetNewAccessTokenUseCase {

    override suspend fun invoke(): AccessToken? =
        tokenRepository.getNewToken()?.accessToken
}
