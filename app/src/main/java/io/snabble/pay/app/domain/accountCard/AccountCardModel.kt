package io.snabble.pay.app.domain.accountCard

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color

public data class AccountCardModel(
    val cardBackgroundColor: List<Color>,
    val qrCodeToken: Bitmap,
    val holderName: String,
    val iban: String,
    val bank: String,
)