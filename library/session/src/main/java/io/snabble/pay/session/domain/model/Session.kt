package io.snabble.pay.session.domain.model

import io.snabble.pay.shared.account.domain.model.Account
import java.time.ZonedDateTime

data class Session(
    val createdAt: ZonedDateTime,
    val id: String,
    val account: Account,
    val token: SessionToken,
    val transaction: Transaction? = null,
)
