package io.snabble.pay.internal.account.data.dto

import kotlinx.serialization.SerialName

enum class MandateStateDto {

    @SerialName("ACCEPTED") ACCEPTED,

    @SerialName("DECLINED") DECLINED,

    @SerialName("MISSING") MISSING,

    @SerialName("PENDING") PENDING,
}
