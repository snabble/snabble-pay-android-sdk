package io.snabble.pay.network

import io.snabble.pay.network.repository.AccessToken
import io.snabble.pay.network.repository.AccessTokenRepository

interface FetchNewAccessTokenUseCase {

    suspend operator fun invoke(): AccessToken
}

class FetchNewAccessTokenUseCaseImpl(
    val credentialsRepository: AppCredentialsRepository,
    val accessTokenRepository: AccessTokenRepository
) : FetchNewAccessTokenUseCase {

    override suspend fun invoke(): AccessToken {
        TODO("Not yet implemented")
    }
}
