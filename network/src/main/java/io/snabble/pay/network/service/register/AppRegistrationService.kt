package io.snabble.pay.network.service.register

import io.snabble.pay.network.service.register.dto.AccessTokenDto
import io.snabble.pay.network.service.register.dto.AppCredentialsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Service related to registering an App instance
 */
interface AppRegistrationService {

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
    @POST("/apps/register")
    fun getAppCredentials(): Call<AppCredentialsDto>

    /**
     * [API Endpoint Documentation](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#App-Authentication)
     */
    @GET("/apps/token")
    fun getAccessToken(
        @Query("grant_type") grantType: String = "client_credentials",
        @Query("client_id") appIdentifier: String,
        @Query("client_secret") appSecret: String,
        @Query("scope") scope: String = "all"
    ): Call<AccessTokenDto>
}