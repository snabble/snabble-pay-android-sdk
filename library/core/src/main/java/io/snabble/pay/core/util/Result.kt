package io.snabble.pay.core.util

import io.snabble.pay.core.PayError

/**
 * Result wrapper for return value.
 *
 * Returns the requested object or `null` on [Success] or an exception, with the responding [PayError] on [Failure].
 * The return value can be accessed via two ways:
 * type check: [Success] or [Failure]
 * properties [isSuccess] or [isFailure], receive the object via the corresponding functions [getOrNull] and [exceptionOrNull]
 *
 * @property isSuccess True if the request for the result has succeeded
 * @property isFailure True if the request for the result has failed
 */
sealed class Result<out T> {

    val isSuccess: Boolean get() = this is Success

    val isFailure: Boolean get() = this is Failure

    fun getOrNull(): T? =
        when {
            isSuccess -> (this as Success).value
            else -> null
        }

    fun exceptionOrNull(): Throwable? =
        when {
            isFailure -> (this as Failure).exception
            else -> null
        }

    companion object {

        fun <T> success(value: T): Result<T> = Success(value)

        fun failure(exception: Throwable, error: PayError): Result<Nothing> =
            Failure(exception, error)
    }
}
/**
 * Represents a result that succeeded, containing the requested object
 */
data class Success<T>(val value: T) : Result<T>()

/**
 * Represents a result that failed, containing the exception, and the [PayError]
 */
data class Failure(val exception: Throwable, val error: PayError) : Result<Nothing>()
