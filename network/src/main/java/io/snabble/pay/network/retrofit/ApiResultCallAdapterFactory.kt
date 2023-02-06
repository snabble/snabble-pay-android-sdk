package io.snabble.pay.network.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class ApiResultCallAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }

        val upperBound = getParameterUpperBound(0, returnType)
        return if (getRawType(upperBound) == ApiResult::class.java && upperBound is ParameterizedType) {
            ApiResponseCallAdapter(type = upperBound)
        } else {
            null
        }
    }

    companion object {

        fun create(): ApiResultCallAdapterFactory = ApiResultCallAdapterFactory()
    }
}

internal class ApiResponseCallAdapter(
    private val type: Type
) : CallAdapter<Type, Call<ApiResult<Type>>> {

    override fun responseType(): Type = type

    override fun adapt(call: Call<Type>): Call<ApiResult<Type>> = ApiResultCall(call)
}
