package io.snabble.pay.network.accesstoken.authenticator

import io.kotest.common.runBlocking
import io.snabble.pay.network.accesstoken.authenticator.usecase.RefreshTokenUseCase
import io.snabble.pay.network.newWithAccessToken
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AccessTokenAuthenticator(
    private val refreshToken: RefreshTokenUseCase
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount > MAX_RETRIES) return null

        val accessToken = runBlocking { refreshToken() } ?: return null

        return response.request.newWithAccessToken(accessToken)
    }

    private companion object {

        const val MAX_RETRIES = 1
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
