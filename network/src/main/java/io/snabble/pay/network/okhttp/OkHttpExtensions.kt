package io.snabble.pay.network.okhttp

import io.snabble.pay.network.repository.AccessToken
import okhttp3.Request

internal fun Request.newWithAuthorizationHeader(token: AccessToken): Request = newBuilder()
    .header(AUTH_HEADER, "$AUTH_HEADER_VALUE ${token.value}")
    .build()

internal const val AUTH_HEADER = "Authorization"
internal const val AUTH_HEADER_VALUE = "Bearer"
