package io.snabble.pay.session.domain.model

/** Enum class representing the state of a [Transaction].*/
enum class TransactionState {

    ABORTED,
    ERRORED,
    FAILED,
    PENDING,
    PREAUTHORIZED,
    PREAUTH_FAILED,
    SUCCESSFUL,
}
