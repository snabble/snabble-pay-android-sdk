package io.snabble.pay.account.domain.model

import java.time.ZonedDateTime

/**
 * This model represents a customer's bank account.
 *
 * All of the data, except for [createdAt] and [id] is provided by the customer's bank.
 *
 * @property bank The name of the financial institution
 * @property createdAt Date and time the account has been added
 * @property currencyCode A three letter currency code.
 * See [this list](https://docs.payone.com/pages/releaseview.action?pageId=1213960).
 * @property holderName The bank account owner's full name.
 * @property iban The bank accounts IBAN.
 * @property id The account ID. _Necessary for several other features of SnabblePay_
 * @property mandateState The current state of the Mandate. See enum [MandateState].
 * @property name Default name for the bank account.
 *
 * @since 1.0.0
 */
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
