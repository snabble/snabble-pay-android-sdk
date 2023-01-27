package io.snabble.pay.network.api.interfaces

import io.snabble.pay.network.api.data.AccessToken
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetAccessToken {

    @GET("/token")
    fun getRefreshToken(
        @Query("grant_type") grantType: String = "client_credentials",
        @Query("client_id") appIdentifier: String,
        @Query("client_secret") appSecret: String,
        @Query("scope") scope: String = "all"
    ): Call<AccessToken>
}
