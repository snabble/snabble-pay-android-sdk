package io.snabble.pay.network.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("AppCredentials")
data class ApiAppCredentials(
    @SerialName("appIdentifier") val appId: String,
    @SerialName("appSecret") val appSecret: String,
    @SerialName("appUrlScheme") val appUrlScheme: String,
)
