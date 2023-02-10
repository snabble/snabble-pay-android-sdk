package io.snabble.pay.core.token.datasource

import io.snabble.pay.network.okhttp.interceptor.AccessToken
import java.time.ZonedDateTime

internal data class TokenDto(
    val accessToken: AccessToken,
    val expiryDate: ZonedDateTime
)

internal fun TokenDto.isValid(at: ZonedDateTime = ZonedDateTime.now()): Boolean =
    expiryDate.isAfter(at)
