package io.snabble.pay.session.data.dto

import io.snabble.pay.api.util.ZonedDateTimeAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SessionDto(
    @SerialName("createdAt") val createdAt: ZonedDateTimeAsText,
    @SerialName("id") val id: String,
    @SerialName("token") val token: TokenDto,
    @SerialName("transaction") val transaction: TransactionDto? = null,
)
