package io.snabble.pay.api.retrofit

import io.snabble.pay.api.model.ErrorDto
import io.snabble.pay.api.model.toPayError
import io.snabble.pay.core.PayError
import io.snabble.pay.core.Reason
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class ApiResultCall<T : Any>(
    private val delegate: Call<T>,
    private val json: Json,
) : Call<ApiResponse<T>> {

    override fun clone(): Call<ApiResponse<T>> = ApiResultCall(delegate.clone(), json)

    override fun execute(): Response<ApiResponse<T>> = throw NotImplementedError()

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        delegate.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = if (response.isSuccessful) {
                    response.toSuccessResponse()
                } else {
                    response.toErrorResponse(json)
                }
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(apiResponse)
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val message: String? = when (t) {
                    is IOException -> "No internet connection"
                    else -> t.localizedMessage
                }
                val fail: ApiResponse<T> = ApiError(
                    rawMessage = message,
                    exception = t,
                    error = PayError(reason = Reason.UNKNOWN, exception = t)
                )
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

internal fun <T : Any> Response<T>.toSuccessResponse(): ApiResponse<T> {
    val body = body()
    return if (body != null) {
        Success(response = this, data = body)
    } else {
        SuccessNoContent(response = this)
    }
}

internal fun <T : Any> Response<in T>.toErrorResponse(json: Json): ApiError {
    val errorBody = errorBody()?.string()
    val error: ErrorDto? = try {
        errorBody?.let<String, ErrorDto>(json::decodeFromString)
    } catch (ignored: SerializationException) {
        null
    }
    return ApiError(
        error = error.toPayError(),
        rawMessage = errorBody,
        exception = HttpException(this)
    )
}
