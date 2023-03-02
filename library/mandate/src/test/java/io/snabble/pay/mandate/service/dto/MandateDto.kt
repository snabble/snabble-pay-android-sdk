package io.snabble.pay.mandate.service.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MandateDto(
    @SerialName("htmlText") val htmlText: String,
    @SerialName("id") val id: String,
    @SerialName("state") val state: MandateStateDto,
)

enum class MandateStateDto {

    @SerialName("ACCEPTED") ACCEPTED,

    @SerialName("DECLINED") DECLINED,

    @SerialName("PENDING") PENDING,
}
