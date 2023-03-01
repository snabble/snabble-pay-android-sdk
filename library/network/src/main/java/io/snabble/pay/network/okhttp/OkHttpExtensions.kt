package io.snabble.pay.network.okhttp

import io.snabble.pay.network.okhttp.interceptor.AccessToken
import okhttp3.Request

internal fun Request.newWithAuthorizationHeader(token: AccessToken): Request = newBuilder()
    .header(AUTH_HEADER, token.value)
    .build()

internal const val AUTH_HEADER = "Authorization"
