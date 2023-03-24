package io.snabble.pay.app.domain.session

import android.os.Parcelable
import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken
import io.snabble.pay.session.domain.model.Transaction
import io.snabble.pay.session.domain.model.TransactionState
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
@Parcelize
data class SessionModel(
    val id: String,
    val token: SessionTokenModel,
    val transaction: TransactionModel?,
) : Parcelable {

    companion object {

        fun from(session: Session) = SessionModel(
            id = session.id,
            token = SessionTokenModel.from(session.token),
            transaction = TransactionModel.from(session.transaction)
        )
    }
}

@Serializable
@Parcelize
data class TransactionModel(
    val id: String,
    val amount: Int,
    val currencyCode: String,
    val state: TransactionState,
) : Parcelable {

    companion object {

        fun from(transaction: Transaction?): TransactionModel? {
            transaction ?: return null
            return TransactionModel(
                id = transaction.id,
                amount = transaction.amount,
                currencyCode = transaction.currencyCode,
                state = transaction.state
            )
        }
    }
}

@Serializable
@Parcelize
data class SessionTokenModel(
    val id: String,
    @Serializable(with = KZonedDateTimeSerializer::class) val refreshAt: ZonedDateTime,
    @Serializable(with = KZonedDateTimeSerializer::class) val validUntil: ZonedDateTime,
    val value: String,
) : Parcelable {

    companion object {

        fun from(sessionToken: SessionToken) = SessionTokenModel(
            id = sessionToken.id,
            refreshAt = sessionToken.refreshAt,
            validUntil = sessionToken.validUntil,
            value = sessionToken.value
        )
    }
}
