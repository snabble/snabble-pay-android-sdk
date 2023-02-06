package io.snabble.pay.network.retrofit

import retrofit2.Response

sealed class ApiResult<out T>

data class ApiSuccess<T : Any>(val data: T, val response: Response<T>) : ApiResult<T>()

data class ApiSuccessNoContent<T>(val response: Response<T>) : ApiResult<Nothing>()

data class ApiError<T : Any>(val response: Response<T>) : ApiResult<T>()

data class Failure(val message: String?, val exception: Throwable) : ApiResult<Nothing>()
