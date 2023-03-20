package io.snabble.pay.api.util

import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.retrofit.SuccessNoContent
import io.snabble.pay.core.util.Result

fun <T : Any, R> ApiResponse<T>.toResult(mapper: (T) -> R): Result<R> = when (this) {
    is Success -> {
        Result.success(mapper(data))
    }

    is SuccessNoContent -> {
        Result.failure(IllegalStateException("Received unexpected 204 NO CONTENT"), value = null)
    }

    is ApiError -> {
        Result.failure(exception, value = error)
    }
}

fun <T : Any, R> ApiResponse<T>.toNullableResult(mapper: (T) -> R): Result<R?> = when (this) {
    is Success -> {
        Result.success(mapper(data))
    }

    is SuccessNoContent -> {
        Result.success(null)
    }

    is ApiError -> {
        Result.failure(exception, value = error)
    }
}
