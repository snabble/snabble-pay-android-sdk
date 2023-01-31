package io.snabble.pay.network.okhttp.interceptor

import io.snabble.pay.network.okhttp.newWithAuthorizationHeader
import io.snabble.pay.network.repository.AccessTokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthorizationHeaderInterceptor(
    private val accessTokenRepository: AccessTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { accessTokenRepository.getAccessToken() }
            ?: return chain.proceed(chain.request())

        return chain.proceed(chain.request().newWithAuthorizationHeader(token))
    }
}
