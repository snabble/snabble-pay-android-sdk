package io.snabble.pay.core.domain.model

import java.time.ZonedDateTime

data class Account(
    val bank: String,
    val createdAt: ZonedDateTime,
    val currencyCode: String,
    val holderName: String,
    val iban: String,
    val id: String,
    val mandateState: MandateState,
    val name: String,
)
