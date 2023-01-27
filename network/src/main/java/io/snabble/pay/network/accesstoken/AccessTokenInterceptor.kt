package io.snabble.pay.network.accesstoken

import io.snabble.pay.network.accesstoken.usecase.ValidateAppUseCase
import io.snabble.pay.network.addAccessToken
import io.snabble.pay.network.newRequestWithAccessToken
import io.snabble.pay.network.requestContainsHeader
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class AccessTokenInterceptor(
    private val accessTokenRepository: AccessTokenRepository,
    private val validateApp: ValidateAppUseCase
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        runBlocking { validateApp() } ?: return appValidationErrorForRequest(chain.request())

        val token = runBlocking { accessTokenRepository.getAccessToken() }
        return if (chain.requestContainsHeader("Authorization")) {
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

    private fun appValidationErrorForRequest(request: Request): Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(401) // status code
        .message("Error: failed to resolve app credentials")
        .body("{}".toResponseBody("application/json; charset=utf-8".toMediaType()))
        .build()
}
