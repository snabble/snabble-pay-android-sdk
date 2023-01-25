package io.snabble.pay.network.accesstoken

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection

internal class AccessTokenInterceptor(
    private val accessTokenRepository: AccessTokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { accessTokenRepository.getAccessToken() }
        val request = chain.newRequestWithAccessToken(token)
        val response = chain.proceed(request)

        return if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val newAccessToken = synchronized(this) {
                // Provide another AccessToken
                AccessToken("other")
            }
            chain.proceed(chain.newRequestWithAccessToken(newAccessToken))
        } else {
            response
        }
    }
}

private fun Interceptor.Chain.newRequestWithAccessToken(token: AccessToken): Request =
    request().newBuilder().addHeader("Authorization", "Bearer $token").build()

internal class AccessTokenAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= MAX_RETRIES) return null

        // TODO: Request new accessToken and add it a header

        return response.request.newBuilder().build()
    }

    private companion object {

        const val MAX_RETRIES = 1
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
