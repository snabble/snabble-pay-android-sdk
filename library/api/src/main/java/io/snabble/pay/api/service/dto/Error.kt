package io.snabble.pay.api.service.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Error(
    @SerialName("error") val error: Data,
) {

    @Serializable
    data class Data(
        @SerialName("message") val message: String,
    )
}
