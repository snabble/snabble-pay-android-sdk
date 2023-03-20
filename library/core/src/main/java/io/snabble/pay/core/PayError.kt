package io.snabble.pay.core

data class PayError(
    val reason: Reason,
    val message: String?,
)

enum class Reason {

    ACCOUNT_NOT_FOUND,
    INVALID_CLIENT,
    INVALID_SESSION_STATE,
    MANDATE_NOT_ACCEPTED,
    SESSION_NOT_FOUND,
    UNAUTHORIZED,
    UNKNOWN,
    VALIDATION_ERROR,
}
