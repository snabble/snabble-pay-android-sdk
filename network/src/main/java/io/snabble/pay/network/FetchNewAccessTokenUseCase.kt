package io.snabble.pay.network.refreshtoken

import io.snabble.pay.network.accesstoken.AccessToken
import io.snabble.pay.network.accesstoken.AccessTokenRepository

interface FetchNewAccessTokenUseCase {

    suspend operator fun invoke(): AccessToken
}

class FetchNewAccessTokenUseCaseImpl(
    val credentialsRepository: AuthCredentialsRepository,
    val accessTokenRepository: AccessTokenRepository
) : FetchNewAccessTokenUseCase {

    override suspend fun invoke(): AccessToken {
        TODO("Not yet implemented")
    }
}
