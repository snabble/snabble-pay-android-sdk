package io.snabble.pay.network.accesstoken.authenticator.usecase

import io.snabble.pay.network.accesstoken.repository.AccessToken

interface RefreshTokenUseCase {
    suspend operator fun invoke() : AccessToken?
}
