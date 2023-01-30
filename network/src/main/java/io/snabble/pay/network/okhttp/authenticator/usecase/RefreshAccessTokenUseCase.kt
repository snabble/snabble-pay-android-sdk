package io.snabble.pay.network.okhttp.authenticator.usecase

import io.snabble.pay.network.repository.AccessToken

interface RefreshAccessTokenUseCase {

    suspend operator fun invoke(): AccessToken?
}
