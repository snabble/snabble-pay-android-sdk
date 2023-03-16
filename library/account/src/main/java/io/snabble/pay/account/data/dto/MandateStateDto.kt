package io.snabble.pay.account.data.dto

import kotlinx.serialization.SerialName

internal enum class MandateStateDto {

    @SerialName("ACCEPTED") ACCEPTED,

    @SerialName("DECLINED") DECLINED,

    @SerialName("MISSING") MISSING,

    @SerialName("PENDING") PENDING,
}
