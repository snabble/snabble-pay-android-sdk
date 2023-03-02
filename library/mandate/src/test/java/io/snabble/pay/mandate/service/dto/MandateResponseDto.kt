package io.snabble.pay.mandate.service.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MandateResponseDto(
    @SerialName("id") val mandateId: String,
    @SerialName("state") val state: MandateResponseStateDto,
)

enum class MandateResponseStateDto {

    @SerialName("ACCEPTED") ACCEPTED,

    @SerialName("PENDING") PENDING,
}
