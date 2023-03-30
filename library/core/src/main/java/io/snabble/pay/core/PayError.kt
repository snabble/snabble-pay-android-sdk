package io.snabble.pay.core

/**
 * Represents the snabble pay error.
 *
 * @property reason Server error with snabble endpoints
 * @property message Describes the reason or contains the exception message
 * @property exception Exception returned by the request
 */
data class PayError(
    val reason: Reason,
    val message: String? = null,
    val exception: Throwable? = null,
)

/**
 * Represents an Error occurring on the pay server.
 */
enum class Reason {

    /**
     * Account for the given id not found
     */
    ACCOUNT_NOT_FOUND,

    /**
     * Customer for the given id not found
     */
    CUSTOMER_NOT_FOUND,

    /**
     * Testing/Debug purpose
     */
    LOCAL_MANDATE,

    /**
     * Internal server error occurred
     */
    INTERNAL_ERROR,

    /**
     * Update token couldn"t be done due to transaction already startet
     */
    INVALID_SESSION_STATE,

    /**
     * Update transaction failed since transaction is PREAUTHORIZED_SUCCESSFUL state
     */
    INVALID_TRANSACTION_STATE,

    /**
     * Session start failed due to missing accepted mandate
     */
    MANDATE_NOT_ACCEPTED,

    /**
     * Delete session has failed due to running transaction
     */
    SESSION_HAS_TRANSACTION,

    /**
     * Session for the given id not found
     */
    SESSION_NOT_FOUND,

    /**
     * Session token is expired, e.g. INVALID
     */
    SESSION_TOKEN_EXPIRED,

    /**
     * Token for the given id not found
     */
    TOKEN_NOT_FOUND,

    /**
     * A transaction is already running for the current session
     */
    TRANSACTION_ALREADY_STARTED,

    /**
     * Transaction for the given id not found
     */
    TRANSACTION_NOT_FOUND,

    /**
     * Access token is expired or invalid
     */
    UNAUTHORIZED,

    /**
     * Unknown error
     */
    UNKNOWN,

    /**
     * User for the given id not found
     */
    USER_NOT_FOUND,

    /**
     * Missing or invalid informatione
     */
    VALIDATION_ERROR,
}
