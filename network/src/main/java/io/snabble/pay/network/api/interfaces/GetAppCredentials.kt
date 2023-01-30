package io.snabble.pay.network.api.interfaces

import io.snabble.pay.network.api.data.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Endpoint to register the application.
 * A successfull call returns a response matching the following pattern:
 * {
 *   "appIdentifier": String,
 *   "appSecret": String,
 *   "appUrlScheme": String
 * }
 */
interface GetAppCredentials {

    @POST("/register")
    fun getAppCredentials(@Body appUrlScheme: String = "snabble-pay"): Call<ApiResponse>
}
