package io.snabble.pay.network

import io.snabble.pay.network.accesstoken.AccessToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal fun Request.Builder.addAccessToken(token: AccessToken): Request.Builder =
    addHeader("Authorization", "Bearer ${token.value}")

internal fun Request.newWithAccessToken(token: AccessToken): Request =
    newBuilder().addHeader("Authorization", "Bearer ${token.value}").build()

internal fun Interceptor.Chain.newRequestWithAccessToken(token: AccessToken): Request =
    request().newBuilder().addHeader("Authorization", "Bearer ${token.value}").build()

internal fun Response.newRequestWithAccessToken(token: AccessToken): Request =
    request.newBuilder().addHeader("Authorization", "Bearer ${token.value}").build()
