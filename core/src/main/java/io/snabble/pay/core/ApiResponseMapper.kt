package io.snabble.pay.core

import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.api.retrofit.Error
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.retrofit.SuccessNoContent

fun <T : Any, R> ApiResponse<T>.toResult(mapper: (T) -> R): Result<R> = when (this) {
    is Success -> {
        Result.success(mapper(data))
    }

    is SuccessNoContent -> {
        Result.failure(IllegalStateException("Received unexpected 204 NO CONTENT"))
    }

    is Error -> {
        Result.failure(exception)
    }
}

fun <T : Any, R> ApiResponse<T>.toNullableResult(mapper: (T) -> R): Result<R?> = when (this) {
    is Success -> {
        Result.success(mapper(data))
    }

    is SuccessNoContent -> {
        Result.success(null)
    }

    is Error -> {
        Result.failure(exception)
    }
}
