package io.snabble.pay.session.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TransactionDto(
    @SerialName("amount") val amount: String,
    @SerialName("currency") val currency: String,
    @SerialName("id") val id: String,
    @SerialName("state") val state: TransactionStateDto,
)
