package io.snabble.pay.internal.token.domain.model

import io.snabble.pay.network.okhttp.interceptor.AccessToken
import java.time.ZonedDateTime

internal data class Token(
    val accessToken: AccessToken,
    val expiryDate: ZonedDateTime,
)

internal fun Token.isValid(at: ZonedDateTime = ZonedDateTime.now()): Boolean =
    expiryDate.isAfter(at)
