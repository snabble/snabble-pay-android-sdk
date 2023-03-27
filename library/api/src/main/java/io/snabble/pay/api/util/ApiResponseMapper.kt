package io.snabble.pay.api.util

import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.retrofit.SuccessNoContent
import io.snabble.pay.core.PayError
import io.snabble.pay.core.Reason
import io.snabble.pay.core.util.Result

fun <T : Any, R> ApiResponse<T>.toResult(mapper: (T) -> R): Result<R> = when (this) {
    is Success -> {
        Result.success(mapper(data))
    }

    is SuccessNoContent -> {
        Result.failure(
            exception = IllegalStateException("Received unexpected 204 NO CONTENT"),
            error = PayError(reason = Reason.UNKNOWN)
        )
    }

    is ApiError -> {
        Result.failure(exception, error = error)
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
        Result.failure(exception, error = error)
    }
}
