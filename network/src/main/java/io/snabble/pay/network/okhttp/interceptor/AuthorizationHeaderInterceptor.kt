package io.snabble.pay.network.okhttp.interceptor

import io.snabble.pay.network.okhttp.newWithAuthorizationHeader
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationHeaderInterceptor(
    private val getAccessToken: GetAccessTokenUseCase
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token: AccessToken = runBlocking { getAccessToken.getAccessToken() }
            ?: return chain.proceed(chain.request())

        return chain.proceed(chain.request().newWithAuthorizationHeader(token))
    }
}
