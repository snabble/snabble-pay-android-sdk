package io.snabble.pay.session.domain.model

import java.time.ZonedDateTime

/**
 * Class representing a Transaction.
 *
 * @property amount The amount of money paid in the transaction
 * @property currencyCode Identifier for the used currency
 * @property createdAt creation time of the transaction
 * @property finalizedAt timestamp when the transaction is finalized
 * @property id Unique identifier for the transaction
 * @property state  Current state of the transaction
 *
 * @since 1.0.0
 */
data class Transaction(
    val amount: Int,
    val createdAt: ZonedDateTime,
    val finalizedAt: ZonedDateTime,
    val currencyCode: String,
    val id: String,
    val state: TransactionState,
)
