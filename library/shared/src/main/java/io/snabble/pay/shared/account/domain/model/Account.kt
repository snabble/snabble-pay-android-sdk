package io.snabble.pay.shared.account.domain.model

import io.snabble.pay.shared.account.domain.model.Account.MandateState
import io.snabble.pay.shared.account.domain.model.Account.MandateState.ACCEPTED
import io.snabble.pay.shared.account.domain.model.Account.MandateState.DECLINED
import io.snabble.pay.shared.account.domain.model.Account.MandateState.MISSING
import io.snabble.pay.shared.account.domain.model.Account.MandateState.PENDING
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
) {

    /**
     * Represents the state of a Mandate.
     *
     * If a new mandate has been created but has been neither accepted nor declined, the state is
     * [PENDING]. After it has been accepted or declined it is in its final state [ACCEPTED] or
     * [DECLINED]. If there is no mandate for the account the state is [MISSING].
     *
     * @since 1.0.0
     */
    enum class MandateState {

        /**
         * Final state if a mandate has been accepted by the customer.
         *
         * @since 1.0.0
         */
        ACCEPTED,

        /**
         * Final state if a mandate has been declined by the customer.
         *
         * @since 1.0.0
         */
        DECLINED,

        /**
         * Initial state for newly added bank account that has no mandate.
         *
         * @since 1.0.0
         */
        MISSING,

        /**
         * State for not yet accepted or declined mandate.
         *
         * @since 1.0.0
         */
        PENDING,
    }
}
