package io.snabble.pay.network.service.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Error(
    @SerialName("error") val error: Data
) {

    @Serializable
    data class Data(
        @SerialName("message") val message: String
    )
}
