package io.snabble.pay.network.service.register.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("AppCredentials")
data class AppCredentialsDto(
    @SerialName("appIdentifier") val appId: String,
    @SerialName("appSecret") val appSecret: String,
)
