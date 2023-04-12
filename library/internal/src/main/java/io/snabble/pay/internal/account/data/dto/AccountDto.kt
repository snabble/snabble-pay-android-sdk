package io.snabble.pay.internal.account.data.dto

import io.snabble.pay.api.util.ZonedDateTimeAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("bank") val bank: String,
    @SerialName("createdAt") val createdAt: ZonedDateTimeAsText,
    @SerialName("currencyCode") val currencyCode: String,
    @SerialName("holderName") val holderName: String,
    @SerialName("iban") val iban: String,
    @SerialName("id") val id: String,
    @SerialName("mandateState") val mandateState: MandateStateDto,
    @SerialName("name") val name: String,
)
