package io.snabble.pay.network.accesstoken.interceptor

import io.snabble.pay.network.accesstoken.repository.AccessTokenRepository
import io.snabble.pay.network.newWithAccessToken
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val accessTokenRepository: AccessTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { accessTokenRepository.getAccessToken() }
            ?: return chain.proceed(chain.request())

        return chain.proceed(chain.request().newWithAccessToken(token))
    }
}
