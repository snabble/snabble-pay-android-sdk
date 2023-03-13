package io.snabble.pay.app.domain.account

import android.os.Parcelable
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.data.entity.AccountCard
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AccountCardModel(
    val accountId: String,
    val holderName: String,
    val iban: String,
    val bank: String,
    val name: String,
    val mandateState: MandateState,
    val cardBackgroundColor: List<String>,
    val qrCodeToken: String?,
) : Parcelable

fun AccountCard.toAccountCardModel() =
    AccountCardModel(
        accountId = id,
        name = name,
        mandateState = mandateState,
        holderName = holderName,
        iban = iban,
        bank = bank,
        cardBackgroundColor = colors,
        qrCodeToken = null
    )
