package io.snabble.pay.network.retrofit

import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class ApiResultCall<T : Any>(private val delegate: Call<T>) : Call<ApiResult<T>> {

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(delegate.clone())

    override fun execute(): Response<ApiResult<T>> = throw NotImplementedError()

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        delegate.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val apiResultResponse =
                    if (response.isSuccessful && body != null) {
                        ApiSuccess(
                            response = response,
                            data = body
                        )
                    } else if (response.isSuccessful && body == null) {
                        ApiSuccessNoContent(response = response)
                    } else {
                        ApiError(response)
                    }
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(apiResultResponse)
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val message: String? = when (t) {
                    is IOException -> "No internet connection"
                    is HttpException -> "None-2xx HTTP response"
                    else -> t.localizedMessage
                }
                val fail: ApiResult<T> = Failure(message = message, exception = t)
                callback.onResponse(this@ApiResultCall, Response.success(fail))
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
