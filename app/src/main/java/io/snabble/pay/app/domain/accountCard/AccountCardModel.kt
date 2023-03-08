package io.snabble.pay.app.domain.accountCard

import io.snabble.pay.app.data.entity.AccountCard
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

fun AccountCard.toAccountCardModel() =
    AccountCardModel(
        accountId = id,
        name = name,
        holderName = holderName,
        iban = iban,
        bank = bank,
        cardBackgroundColor = colors,
        qrCodeToken = null
    )
