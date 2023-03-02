package io.snabble.pay.api.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultCallAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType
        ) {
            return null
        }

        val upperBound = getParameterUpperBound(0, returnType)
        return if (getRawType(upperBound) == ApiResponse::class.java &&
            upperBound is ParameterizedType
        ) {
            val dataType = getParameterUpperBound(0, upperBound)
            createAdapter(type = dataType)
        } else {
            null
        }
    }

    internal fun createAdapter(type: Type) =
        ApiResponseCallAdapter(type = type)

    companion object {

        fun create(): ApiResultCallAdapterFactory = ApiResultCallAdapterFactory()
    }
}
