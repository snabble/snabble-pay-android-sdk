package io.snabble.pay.network

import io.snabble.pay.network.repository.AccessToken
import okhttp3.Request

internal fun Request.newWithAuthenticationHeader(token: AccessToken): Request = newBuilder()
    .removeHeader(AUTH_HEADER)
    .header(AUTH_HEADER, "$AUTH_HEADER_VALUE ${token.value}")
    .build()

internal const val AUTH_HEADER = "Authorization"
internal const val AUTH_HEADER_VALUE = "Bearer"
