package io.snabble.pay.customerinfo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CustomerInfoDto(
    @SerialName("id") val id: String? = null,
    @SerialName("loyaltyId") val loyaltyId: String? = null,
)
