package io.snabble.pay.app.domain.accountCard

import kotlinx.serialization.Serializable

@Serializable
data class AccountCardModel(
    val accountId: String,
    val holderName: String,
    val iban: String,
    val bank: String,
    val name: String,
    val cardBackgroundColor: List<String>,
    val qrCodeToken: String?,
)
