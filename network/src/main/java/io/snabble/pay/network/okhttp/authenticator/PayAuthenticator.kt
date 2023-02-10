package io.snabble.pay.network.okhttp.authenticator

import io.snabble.pay.network.okhttp.newWithAuthorizationHeader
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class PayAuthenticator(
    private val getNewAccessToken: GetNewAccessTokenUseCase
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= MAX_ATTEMPTS) return null
        val accessToken = runBlocking { getNewAccessToken() } ?: return null

        return response.request.newWithAuthorizationHeader(accessToken)
    }

    private companion object {

        const val MAX_ATTEMPTS = 2
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
