package io.snabble.pay.network.service.register.dto

import io.snabble.pay.network.util.LocalDateAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenDto(
    @SerialName("accessToken") val token: String,
    @SerialName("expiresAt") val expiryDate: LocalDateAsText,
    @SerialName("tokenType") val tokenType: String,
)
