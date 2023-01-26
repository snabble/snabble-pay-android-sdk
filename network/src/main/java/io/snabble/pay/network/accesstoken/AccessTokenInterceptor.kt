package io.snabble.pay.network.accesstoken

import io.snabble.pay.network.addAccessToken
import io.snabble.pay.network.newRequestWithAccessToken
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AccessTokenInterceptor(
    private val accessTokenRepository: AccessTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { accessTokenRepository.getAccessToken() }
        return if (hasAuthorizationHeader(chain)) {
            chain.proceed(
                chain.request().newBuilder()
                    .removeHeader("Authorization")
                    .addAccessToken(token)
                    .build()
            )
        } else {
            chain.proceed(chain.newRequestWithAccessToken(token))
        }
    }

    private fun hasAuthorizationHeader(chain: Interceptor.Chain) =
        chain.request().headers.any { it.first == "Authorization" }
}
