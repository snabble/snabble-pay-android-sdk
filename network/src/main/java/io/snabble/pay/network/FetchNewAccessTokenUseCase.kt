package io.snabble.pay.network

import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.okhttp.interceptor.AccessTokenProvider

interface FetchNewAccessTokenUseCase {

    suspend operator fun invoke(): AccessToken
}

class FetchNewAccessTokenUseCaseImpl(
    val accessTokenProvider: AccessTokenProvider
) : FetchNewAccessTokenUseCase {

    override suspend fun invoke(): AccessToken {
        TODO("Not yet implemented")
    }
}
