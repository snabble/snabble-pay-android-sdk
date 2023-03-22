package io.snabble.pay.app.data.utils

import io.snabble.pay.core.PayError
import io.snabble.pay.core.Reason
import java.io.IOException

sealed interface AppResult<T>

data class AppSuccess<T>(val value: T) : AppResult<T>

data class AppError<T>(val value: ErrorResponse?) : AppResult<T>

inline fun <T> AppResult<T>.onSuccess(block: (T) -> Unit): AppResult<T> {
    val result = this
    if (result is AppSuccess<T>) {
        block(result.value)
    }
    return this
}

inline fun <T> AppResult<T>.onError(block: (ErrorResponse?) -> Unit): AppResult<T> {
    val result = this
    if (result is AppError<T>) {
        block(result.value)
    }
    return this
}

data class ErrorResponse(
    val reason: Reason,
    val message: String?,
)

fun PayError.toErrorResponse(): ErrorResponse {
    val ex = exception
    return if (reason == Reason.UNKNOWN && ex is IOException) {
        ErrorResponse(reason = reason, message = ex.message)
    } else {
        ErrorResponse(reason, message)
    }
}
