package io.snabble.pay.core.util

import io.snabble.pay.core.PayError

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

data class Success<T>(val value: T) : Result<T>()

data class Failure(val exception: Throwable, val error: PayError) : Result<Nothing>()
