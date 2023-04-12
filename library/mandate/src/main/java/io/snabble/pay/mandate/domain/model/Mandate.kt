package io.snabble.pay.mandate.domain.model

/**
 * Class representing the Mandate.
 *
 * A Mandate's property is optional, since it is not required if the mandate is already accepted or declined.
 *
 * @property htmlText Mandate terms to be accepted or declined
 * @property id Unique identifier for the mandate
 * @property state Current state of the mandate, e.g.
 * [PENDING](io.snabble.pay.mandate.domain.model.MandateState.PENDING),
 * [ACCEPTED](io.snabble.pay.mandate.domain.model.MandateState.ACCEPTED) or
 * [DECLINED](io.snabble.pay.mandate.domain.model.MandateState.DECLINED)
 *
 * @since 1.0.0
 */
class Mandate(
    val htmlText: String?,
    val id: String,
    val state: State,
) {

    /**
     * Represents the state of a Mandate.
     *
     * If a new mandate has been created but has been neither accepted nor declined, the state is
     * [PENDING]. After it has been accepted or declined it is in its final state [ACCEPTED] or
     * [DECLINED].
     *
     * @since 1.0.0
     */
    enum class State {

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
         * State for not yet accepted or declined mandate.
         *
         * @since 1.0.0
         */
        PENDING,
    }
}
