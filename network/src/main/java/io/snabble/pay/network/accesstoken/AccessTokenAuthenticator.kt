package io.snabble.pay.network.accesstoken

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AccessTokenAuthenticator : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.responseCount > MAX_RETRIES) return null

        return response.request
    }

    private companion object {

        const val MAX_RETRIES = 1
    }
}

val Response.responseCount: Int
    get() = generateSequence(this) { it.priorResponse }.count()
