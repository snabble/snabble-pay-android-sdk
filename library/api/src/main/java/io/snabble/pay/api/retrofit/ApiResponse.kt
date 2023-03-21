package io.snabble.pay.api.retrofit

import io.snabble.pay.core.PayError
import retrofit2.Response

sealed class ApiResponse<out T : Any>

data class Success<T : Any>(val data: T, val response: Response<T>) : ApiResponse<T>()

data class SuccessNoContent(val response: Response<*>) : ApiResponse<Nothing>()

data class ApiError(
    val error: PayError? = null,
    val rawMessage: String? = null,
    val exception: Throwable,
) : ApiResponse<Nothing>()
