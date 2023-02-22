package io.snabble.pay.network.okhttp.interceptor

interface GetAccessTokenUseCase {

    suspend operator fun invoke(): AccessToken?
}

@JvmInline
value class AccessToken(val value: String)
