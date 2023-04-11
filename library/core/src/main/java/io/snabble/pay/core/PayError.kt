package io.snabble.pay.core

data class PayError(
    val reason: Reason,
    val message: String? = null,
    val exception: Throwable? = null,
)

enum class Reason {

    ACCOUNT_NOT_FOUND,
    CUSTOMER_NOT_FOUND,
    LOCAL_MANDATE,
    INTERNAL_ERROR,
    INVALID_SESSION_STATE,
    INVALID_TRANSACTION_STATE,
    MANDATE_NOT_ACCEPTED,
    MANDATE_NOT_FOUND,
    SESSION_HAS_TRANSACTION,
    SESSION_NOT_FOUND,
    SESSION_TOKEN_EXPIRED,
    TOKEN_NOT_FOUND,
    TRANSACTION_ALREADY_STARTED,
    TRANSACTION_NOT_FOUND,
    UNAUTHORIZED,
    UNKNOWN,
    USER_NOT_FOUND,
    VALIDATION_ERROR,
}
