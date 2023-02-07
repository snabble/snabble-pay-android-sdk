package io.snabble.pay.network.retrofit

import retrofit2.Response

sealed class ApiResponse<out T>

data class Success<T : Any>(val data: T, val response: Response<T>) : ApiResponse<T>()

data class SuccessNoContent<T : Any>(val response: Response<T>) : ApiResponse<T>()

data class Error<T : Any>(val message: String?, val exception: Throwable) : ApiResponse<T>()
