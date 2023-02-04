package io.snabble.pay.core.accesstoken.data.source.dto

import io.snabble.pay.network.okhttp.interceptor.AccessToken
import java.time.ZonedDateTime

data class TokenDto(
    val accessToken: AccessToken,
    val expiryDate: ZonedDateTime
)
