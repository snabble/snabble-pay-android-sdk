package io.snabble.pay.session.domain.model

/**
 * Represents the state of a [Transaction].
 *
 * @since 1.0.0
 */
enum class TransactionState {

    /**
     * Final state if a transaction has been aborted by the customer.
     *
     * @since 1.0.0
     */
    ABORTED,

    /**
     * Final state if a transaction ended in an error state.
     *
     * @since 1.0.0
     */
    ERRORED,

    /**
     * Final state if a transaction has failed.
     *
     * @since 1.0.0
     */
    FAILED,

    /**
     * State if a transaction is still in process.
     *
     * @since 1.0.0
     */
    PENDING,

    /**
     * Pre state if a transaction needs to be authorized.
     *
     * @since 1.0.0
     */
    PREAUTHORIZED,

    /**
     * Final state if a transaction authorization failed.
     *
     * @since 1.0.0
     */
    PREAUTH_FAILED,

    /**
     * Final state if a transaction has been successfully finished by the customer.
     *
     * @since 1.0.0
     */
    SUCCESSFUL,
}
