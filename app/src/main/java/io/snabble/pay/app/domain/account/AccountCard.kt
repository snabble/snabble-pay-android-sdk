package io.snabble.pay.app.domain.account

import android.os.Parcelable
import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.domain.account.utils.GradiantGenerator
import io.snabble.pay.app.domain.session.SessionModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AccountCard(
    val accountId: String,
    val holderName: String,
    val iban: String,
    val bank: String,
    val name: String,
    val mandateState: MandateState,
    val cardBackgroundColor: List<String>,
    val session: SessionModel?,
) : Parcelable

fun Account.toAccountCard(savedName: String? = null, colors: List<String>?): AccountCard = AccountCard(
    accountId = id,
    name = if (savedName.isNullOrEmpty()) name else savedName,
    mandateState = mandateState,
    holderName = holderName,
    iban = iban,
    bank = bank,
    cardBackgroundColor = if (colors.isNullOrEmpty()) GradiantGenerator.createGradiantColorList() else colors,
    session = null
)
