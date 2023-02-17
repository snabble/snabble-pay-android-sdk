package io.snabble.pay.network.retrofit

import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class ApiResultCall<T : Any>(private val delegate: Call<T>) : Call<ApiResponse<T>> {

    override fun clone(): Call<ApiResponse<T>> = ApiResultCall(delegate.clone())

    override fun execute(): Response<ApiResponse<T>> = throw NotImplementedError()

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        delegate.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = response.toApiResponse()
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(apiResponse),
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val message: String? = when (t) {
                    is IOException -> "No internet connection"
                    else -> t.localizedMessage
                }
                val fail: ApiResponse<T> = Error(message = message, exception = t)
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

internal fun <T : Any> Response<T>.toApiResponse(): ApiResponse<T> {
    if (!isSuccessful) return Error(message = message(), exception = HttpException(this))

    val body = body()
    return if (body != null) {
        Success(response = this, data = body)
    } else {
        SuccessNoContent(response = this)
    }
}
