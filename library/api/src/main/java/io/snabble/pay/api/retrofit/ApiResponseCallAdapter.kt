package io.snabble.pay.api.retrofit

import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ApiResponseCallAdapter(
    private val type: Type,
    private val json: Json,
) : CallAdapter<Type, Call<ApiResponse<Type>>> {

    override fun responseType(): Type = type

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> = ApiResultCall(call, json)
}
