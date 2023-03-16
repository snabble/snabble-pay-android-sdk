package io.snabble.pay.session.domain.model

enum class TransactionState {

    ABORTED,
    ERRORED,
    FAILED,
    PENDING,
    PREAUTHORIZED,
    PREAUTH_FAILED,
    SUCCESSFUL,
}
