package io.snabble.pay.mandate.data.dto

import kotlinx.serialization.SerialName

internal enum class MandateStateDto {

    @SerialName("ACCEPTED") ACCEPTED,

    @SerialName("DECLINED") DECLINED,

    @SerialName("PENDING") PENDING,
}
