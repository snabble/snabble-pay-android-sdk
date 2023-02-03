package io.snabble.pay.core.accesstoken.data.source.dto

import io.snabble.pay.network.okhttp.interceptor.AccessToken

data class AccessTokenDto(
    val accessToken: AccessToken,
    val expiryDate: String
)
