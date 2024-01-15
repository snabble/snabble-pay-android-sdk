package io.snabble.pay.app.domain.transactions

import java.time.ZonedDateTime

data class PurchasesModel(
    val data: ZonedDateTime,
    val amount: Int,
    val cardName: String,
    val state: PurchasesState,
)

enum class PurchasesState {
    SUCCESS, FAILED
}
