package io.snabble.pay.network.okhttp.interceptor

import io.snabble.pay.network.okhttp.newWithAuthorizationHeader
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthorizationHeaderInterceptor(
    private val accessTokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token: AccessToken = runBlocking { accessTokenProvider.getAccessToken() }
            ?: return chain.proceed(chain.request())

        return chain.proceed(chain.request().newWithAuthorizationHeader(token))
    }
}
