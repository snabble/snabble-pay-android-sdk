package io.snabble.pay.session.domain.model

import java.time.ZonedDateTime

/** Class representing a session.
 *
 *  A session provides the token containing information to start transactions and
 *  can only be started if the [Mandate](io.snabble.pay.mandate.domain.model.Mandate) is accepted.
 *  A transaction is by default null until a transaction is started.
 *
 * @property createdAt Timestamp of the creation
 * @property id Unique identifier for the session
 * @property token token information associated with the session
 * @property transaction transaction information associated with session
 *
 * @since 1.0.0
 */
data class Session(
    val createdAt: ZonedDateTime,
    val id: String,
    val token: SessionToken,
    val transaction: Transaction? = null,
)
