package io.snabble.pay.session.domain.model

enum class TransactionState {

    ABORTED,
    ERRORED,
    FAILED,
    PREAUTHORIZED,
    PREAUTH_FAILED,
    SUCCESSFUL,
}
