package io.snabble.pay.session.data.dto

import io.snabble.pay.api.util.ZonedDateTimeAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenDto(
    @SerialName("createdAt") val createdAt: ZonedDateTimeAsText,
    @SerialName("id") val id: String,
    @SerialName("refreshAt") val refreshAt: ZonedDateTimeAsText,
    @SerialName("validUntil") val validUntil: ZonedDateTimeAsText,
    @SerialName("value") val value: String,
)
