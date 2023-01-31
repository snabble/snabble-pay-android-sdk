package io.snabble.pay.network.api.interfaces

import io.snabble.pay.network.api.data.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Service related to registering an App instance
 */
interface RegistrationApi {

    /**
     * Endpoint to register the application.
     * A successfull call returns a response matching the following pattern:
     * {
     *   "appIdentifier": String,
     *   "appSecret": String,
     *   "appUrlScheme": String
     * }
     *
     * [API Endpoint Documentation](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#App-Registration)
     */
    @POST("/app/register")
    fun getAppCredentials(@Body appUrlScheme: String): Call<ApiResponse>
}
