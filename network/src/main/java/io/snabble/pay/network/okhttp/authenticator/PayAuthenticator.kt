package io.snabble.pay.network.okhttp.authenticator

import io.snabble.pay.network.okhttp.authenticator.usecase.RefreshAccessTokenUseCase
import io.snabble.pay.network.newWithAuthenticationHeader
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

internal class PayAuthenticator(
    private val refreshAccessToken: RefreshAccessTokenUseCase
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= MAX_ATTEMPTS) return null
        val accessToken = runBlocking { refreshAccessToken() } ?: return null

        return response.request.newWithAuthenticationHeader(accessToken)
    }

    private companion object {

        const val MAX_ATTEMPTS = 2
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
