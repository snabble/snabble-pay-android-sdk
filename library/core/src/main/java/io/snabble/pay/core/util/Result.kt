package io.snabble.pay.core.util

sealed class Result<out T> {

    val isSuccess: Boolean get() = this is Success

    val isFailure: Boolean get() = this is Failure<*>

    fun getOrNull(): T? =
        when {
            isSuccess -> (this as Success).value
            else -> null
        }

    fun exceptionOrNull(): Throwable? =
        when {
            isFailure -> (this as Failure<*>).exception
            else -> null
        }

    companion object {

        fun <T> success(value: T): Result<T> = Success(value)

        fun <T> failure(exception: Throwable, value: T? = null): Result<Nothing> =
            Failure(exception, value)
    }
}

data class Success<T : Any?>(val value: T) : Result<T>()

data class Failure<T : Any?>(val exception: Throwable, val value: T) : Result<Nothing>()
