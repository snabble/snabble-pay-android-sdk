package io.snabble.pay.session.domain.model

import java.time.ZonedDateTime

data class SessionToken(
    val createdAt: ZonedDateTime,
    val id: String,
    val refreshAt: ZonedDateTime,
    val validUntil: ZonedDateTime,
    val value: String,
)
