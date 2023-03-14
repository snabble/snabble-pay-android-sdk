package io.snabble.pay.session.domain.model

data class Transaction(
    val amount: String,
    val currency: String,
    val id: String,
    val state: TransactionState,
)
