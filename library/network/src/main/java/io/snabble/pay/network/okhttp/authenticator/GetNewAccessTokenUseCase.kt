package io.snabble.pay.network.okhttp.authenticator

import io.snabble.pay.network.okhttp.interceptor.AccessToken

interface GetNewAccessTokenUseCase {

    suspend operator fun invoke(): AccessToken?
}
