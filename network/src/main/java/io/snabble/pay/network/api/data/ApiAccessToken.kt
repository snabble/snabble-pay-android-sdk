package io.snabble.pay.network.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiAccessToken(
    @SerialName("token") val token: String
)

@Serializable
data class ApiResponse(
    @SerialName("AppCredentials") val credentials: ApiAppCredentials
)
