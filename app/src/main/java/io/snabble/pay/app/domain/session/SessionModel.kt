package io.snabble.pay.app.domain.session

import io.snabble.pay.session.domain.model.Session
import io.snabble.pay.session.domain.model.SessionToken
import io.snabble.pay.session.domain.model.Transaction
import io.snabble.pay.session.domain.model.TransactionState
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

@Serializable
data class SessionModel(
    val id: String,
    val token: SessionTokenModel,
    val transaction: TransactionModel?,
) {

    companion object {

        fun from(session: Session) = SessionModel(
            id = session.id,
            token = SessionTokenModel.from(session.token),
            transaction = TransactionModel.from(session.transaction)
        )
    }
}

@Serializable
data class TransactionModel(
    val id: String,
    val amount: Int,
    val currencyCode: String,
    val state: TransactionState,
) {

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
data class SessionTokenModel(
    val id: String,
    @Serializable(with = KZonedDateTimeSerializer::class) val refreshAt: ZonedDateTime,
    @Serializable(with = KZonedDateTimeSerializer::class) val validUntil: ZonedDateTime,
    val value: String,
) {

    companion object {

        fun from(sessionToken: SessionToken) = SessionTokenModel(
            id = sessionToken.id,
            refreshAt = sessionToken.refreshAt,
            validUntil = sessionToken.validUntil,
            value = sessionToken.value
        )
    }
}
