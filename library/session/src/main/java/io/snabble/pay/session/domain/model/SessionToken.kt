package io.snabble.pay.session.domain.model

import java.time.ZonedDateTime

/**
 * Class representing a token.
 *
 * Contains information and timestamps to trigger transactions and refresh`s.
 *
 * @property createdAt Timestamp of the creation
 * @property expiresAt Timestamp indicating when the token is invalid
 * @property id Unique identifier for the session token
 * @property refreshAt Timestamp indicating refresh is available
 * @property value Encrypted information for transactions
 *
 * @since 1.0.0
 */
data class SessionToken(
    val createdAt: ZonedDateTime,
    val expiresAt: ZonedDateTime,
    val id: String,
    val refreshAt: ZonedDateTime,
    val value: String,
)
