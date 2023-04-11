package io.snabble.pay.core

/**
 * Represents the snabble pay error.
 *
 * @property reason Server error with snabble endpoints
 * @property message Describes the reason or contains the exception message
 * @property exception Exception returned by the request
 *
 * @since 1.0.0
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
     *
     * @since 1.0.0
     */
    ACCOUNT_NOT_FOUND,

    /**
     * Customer for the given id not found
     *
     * @since 1.0.0
     */
    CUSTOMER_NOT_FOUND,

    /**
     * Testing/Debug purpose
     *
     * @since 1.0.0
     */
    LOCAL_MANDATE,

    /**
     * Internal server error occurred
     *
     * @since 1.0.0
     */
    INTERNAL_ERROR,

    /**
     * Update token couldn't be done due to transaction already started
     *
     * @since 1.0.0
     */
    INVALID_SESSION_STATE,

    /**
     * Update transaction failed since transaction is PREAUTHORIZED_SUCCESSFUL state
     *
     * @since 1.0.0
     */
    INVALID_TRANSACTION_STATE,

    /**
     * Session start failed due to missing accepted mandate
     *
     * @since 1.0.0
     */
    MANDATE_NOT_ACCEPTED,

    /**
     * Delete session has failed due to running transaction
     *
     * @since 1.0.0
     */
    SESSION_HAS_TRANSACTION,

    /**
     * Session for the given id not found
     *
     * @since 1.0.0
     */
    SESSION_NOT_FOUND,

    /**
     * Session token is expired, e.g. INVALID
     *
     * @since 1.0.0
     */
    SESSION_TOKEN_EXPIRED,

    /**
     * Token for the given id not found
     *
     * @since 1.0.0
     */
    TOKEN_NOT_FOUND,

    /**
     * A transaction is already running for the current session
     *
     * @since 1.0.0
     */
    TRANSACTION_ALREADY_STARTED,

    /**
     * Transaction for the given id not found
     *
     * @since 1.0.0
     */
    TRANSACTION_NOT_FOUND,

    /**
     * Access token is expired or invalid
     *
     * @since 1.0.0
     */
    UNAUTHORIZED,

    /**
     * Unknown error
     *
     * @since 1.0.0
     */
    UNKNOWN,

    /**
     * User for the given id not found
     *
     * @since 1.0.0
     */
    USER_NOT_FOUND,

    /**
     * Missing or invalid information
     *
     * @since 1.0.0
     */
    VALIDATION_ERROR,
}
