package io.snabble.pay.session.domain.model

/** Represents the state of a [Transaction].*/
enum class TransactionState {

    /**
     * Final state if a transaction has been aborted by the customer.
     */
    ABORTED,

    /**
     * Final state if a transaction ended in an error state.
     */
    ERRORED,

    /**
     * Final state if a transaction has failed.
     */
    FAILED,

    /**
     * State if a transaction is still in process.
     */
    PENDING,

    /**
     * Pre state if a transaction needs to be authorized.
     */
    PREAUTHORIZED,

    /**
     * Final state if a transaction authorization failed.
     */
    PREAUTH_FAILED,

    /**
     * Final state if a transaction has been successfully finished by the customer.
     */
    SUCCESSFUL,
}
