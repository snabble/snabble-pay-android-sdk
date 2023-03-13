package io.snabble.pay.mandate.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MandateDto(
    @SerialName("htmlText") val htmlText: String? = null,
    @SerialName("id") val id: String,
    @SerialName("state") val state: MandateStateDto,
)
