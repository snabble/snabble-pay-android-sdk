package io.snabble.pay.session.domain.model

import java.time.ZonedDateTime

data class Session(
    val createdAt: ZonedDateTime,
    val id: String,
    val token: SessionToken,
    val transaction: Transaction? = null,
)
