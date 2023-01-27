package io.snabble.pay.network.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("token") val token: String
)

@Serializable
data class Response(
    @SerialName("AppCredentials") val credentials: AppCredentials
)
