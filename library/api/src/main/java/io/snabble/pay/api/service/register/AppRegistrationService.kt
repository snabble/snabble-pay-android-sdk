package io.snabble.pay.api.service.register

import io.snabble.pay.api.retrofit.ApiResponse
import io.snabble.pay.api.service.register.dto.AppCredentialsDto
import io.snabble.pay.api.service.register.dto.TokenDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Service related to registering an App instance to be able to use Snabble Pay.
 */
interface AppRegistrationService {

    /**
     * The app is registered at Snabble Pay. The credentials are needed to request a token that's
     * necessary for all interactions with the Snabble Pay API.
     *
     * [Docs to register an app](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#App-Registration)
     */
    @POST("/apps/register")
    suspend fun getAppCredentials(
        @Header("snabblePayKey") key: String,
    ): ApiResponse<AppCredentialsDto>

    /**
     * Request a new token. The token provides access to all other interactions with
     * the Snabble Pay API.
     *
     * [Docs to authenticate the app](https://snabble.atlassian.net/wiki/spaces/PAYMENT/pages/131301398/Backend+Requirements#App-Authentication)
     */
    @GET("/apps/token")
    suspend fun getToken(
        @Query("grant_type") grantType: String = "client_credentials",
        @Query("client_id") appIdentifier: String,
        @Query("client_secret") appSecret: String,
        @Query("scope") scope: String = "all",
    ): ApiResponse<TokenDto>
}
