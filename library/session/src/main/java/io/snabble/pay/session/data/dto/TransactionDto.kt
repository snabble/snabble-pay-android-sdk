package io.snabble.pay.session.data.dto

import io.snabble.pay.api.util.ZonedDateTimeAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionDto(
    @SerialName("amount") val amount: Int,
    @SerialName("currencyCode") val currencyCode: String,
    @SerialName("createdAt") val createdAt: ZonedDateTimeAsText,
    @SerialName("finalizedAt") val finalizedAt: ZonedDateTimeAsText,
    @SerialName("id") val id: String,
    @SerialName("state") val state: TransactionStateDto,
)
