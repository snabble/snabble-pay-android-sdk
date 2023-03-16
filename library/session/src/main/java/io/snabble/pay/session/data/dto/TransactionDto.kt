package io.snabble.pay.session.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionDto(
    @SerialName("amount") val amount: Int,
    @SerialName("currencyCode") val currencyCode: String,
    @SerialName("id") val id: String,
    @SerialName("state") val state: TransactionStateDto,
)
