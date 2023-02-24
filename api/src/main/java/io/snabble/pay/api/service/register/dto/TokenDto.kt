package io.snabble.pay.api.service.register.dto

import io.snabble.pay.api.util.ZonedDateTimeAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    @SerialName("accessToken") val token: String,
    @SerialName("expiresAt") val expiryDate: ZonedDateTimeAsText,
    @SerialName("tokenType") val tokenType: String,
)
