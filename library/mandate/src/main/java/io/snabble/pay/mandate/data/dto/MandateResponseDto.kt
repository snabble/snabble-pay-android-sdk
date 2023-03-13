package io.snabble.pay.mandate.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MandateResponseDto(
    @SerialName("id") val mandateId: String,
    @SerialName("state") val state: MandateStateDto,
)
