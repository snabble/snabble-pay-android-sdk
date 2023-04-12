package io.snabble.pay.mandate.domain.model

/**
 * Represents the state of a Mandate.
 *
 * If a new mandate has been created but has been neither accepted nor declined, the state is
 * [PENDING]. After it has been accepted or declined it is in its final state [ACCEPTED] or
 * [DECLINED].
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
     * State for not yet accepted or declined mandate.
     *
     * @since 1.0.0
     */
    PENDING,
}
