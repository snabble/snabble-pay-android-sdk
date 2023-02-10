package io.snabble.pay.network.okhttp.interceptor

interface GetAccessTokenUseCase {

    suspend fun getAccessToken(): AccessToken?
}

@JvmInline
value class AccessToken(val value: String)
