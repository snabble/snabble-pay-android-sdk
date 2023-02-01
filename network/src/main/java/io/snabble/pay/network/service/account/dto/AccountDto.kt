package io.snabble.pay.network.service.account.dto

import io.snabble.pay.network.util.LocalDateAsText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    @SerialName("bank") val bank: String,
    @SerialName("createdAt") val createdAt: LocalDateAsText,
    @SerialName("currencyCode") val currencyCode: String,
    @SerialName("holderName") val holderName: String,
    @SerialName("iban") val iban: String,
    @SerialName("id") val id: String,
    @SerialName("mandate") val mandate: MandateDto,
    @SerialName("name") val name: String
) {

    @Serializable
    data class MandateDto(
        @SerialName("state") val state: String,
        @SerialName("text") val text: String
    )
}
