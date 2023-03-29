package io.snabble.pay.account.domain.model

/**
 * Represents the state of a Mandate.
 *
 * The initial mandate state of a new bank account is [MISSING]. If a new mandate has been created
 * but has been neither accepted nor declined, the state is [PENDING]. After it has been accepted
 * or declined it is in its final state [ACCEPTED] or [DECLINED].
 */
enum class MandateState {

    /**
     * Final state if a mandate has been accepted by the customer.
     */
    ACCEPTED,

    /**
     * Final state if a mandate has been declined by the customer.
     */
    DECLINED,

    /**
     * Initial state for newly added bank account that has no mandate.
     */
    MISSING,

    /**
     * Initial state for a new mandate.
     */
    PENDING,
}
