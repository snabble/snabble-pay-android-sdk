package io.snabble.pay.app.domain.accountCard

import androidx.compose.ui.graphics.Color

data class AccountCardModel(
    val cardBackgroundColor: List<Color>,
    val qrCodeToken: String?,
    val holderName: String,
    val iban: String,
    val bank: String,
)