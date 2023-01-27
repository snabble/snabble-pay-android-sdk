package io.snabble.pay.network.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("AppCredentials")
data class AppCredentials(
    @SerialName("appIdentifier") val appId: String,
    @SerialName("appSecret") val appSecret: String,
    @SerialName("appUrlScheme") val appUrlScheme: String,

)
