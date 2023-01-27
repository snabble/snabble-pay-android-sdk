package io.snabble.pay.network.accesstoken.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?

    suspend fun saveAppCredentials(appCredentials: AppCredentials)
}


@Serializable
@SerialName("AppCredentials")
data class AppCredentials(
    @SerialName("appIdentifier") val appId: String,
    @SerialName("appSecret") val appSecret: String,
    @SerialName("appUrlScheme") val appUrlScheme: String,

)
