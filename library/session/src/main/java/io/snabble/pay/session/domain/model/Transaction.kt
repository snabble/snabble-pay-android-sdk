package io.snabble.pay.session.domain.model

data class Transaction(
    val amount: Int,
    val currencyCode: String,
    val id: String,
    val state: TransactionState,
)
