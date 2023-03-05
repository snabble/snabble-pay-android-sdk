package io.snabble.pay.app.domain.accountCard

import kotlinx.serialization.Serializable

@Serializable
data class AccountCardModel(
    val cardBackgroundColor: List<String>,
    val qrCodeToken: String?,
    val holderName: String,
    val iban: String,
    val bank: String,
)
