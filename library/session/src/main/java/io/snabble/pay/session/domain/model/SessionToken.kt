package io.snabble.pay.session.domain.model

import java.time.ZonedDateTime

/**
 * Class representing a token.
 *
 * Contains information and timestamps to trigger transactions and refresh`s.
 *
 * @property createdAt Timestamp of the creation
 * @property id Unique identifier for the session token
 * @property refreshAt Timestamp indicating refresh is available
 * @property validUntil Timestamp indicating that the token is invalid
 * @property value Encrypted information for transactions
 */
data class SessionToken(
    val createdAt: ZonedDateTime,
    val id: String,
    val refreshAt: ZonedDateTime,
    val validUntil: ZonedDateTime,
    val value: String,
)
