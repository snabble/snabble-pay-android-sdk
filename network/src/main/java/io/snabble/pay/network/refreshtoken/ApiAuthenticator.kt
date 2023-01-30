package io.snabble.pay.network.refreshtoken

import io.snabble.pay.network.newWithAccessToken
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

internal class ApiAuthenticator(
    val fetchNewAccessToken: FetchNewAccessTokenUseCase
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.responseCount >= MAX_RETRIES) return null

        val accessToken = runBlocking { fetchNewAccessToken() }

        return response.request.newWithAccessToken(accessToken)
    }

    private companion object {

        const val MAX_RETRIES = 1
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
