package io.snabble.pay.network.accesstoken.authenticator

import io.kotest.common.runBlocking
import io.snabble.pay.network.accesstoken.authenticator.usecase.RefreshTokenUseCase
import io.snabble.pay.network.newRequestWithAccessToken
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AccessTokenAuthenticator(
    private val refreshToken: RefreshTokenUseCase
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.responseCount > MAX_RETRIES) return null

        val accessToken = runBlocking { refreshToken() }

        accessToken ?: return null

        return response.newRequestWithAccessToken(accessToken)
    }

    private companion object {

        const val MAX_RETRIES = 1
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
