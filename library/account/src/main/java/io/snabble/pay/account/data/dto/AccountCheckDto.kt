package io.snabble.pay.account.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AccountCheckDto(
    @SerialName("appUri") val appUri: String,
    @SerialName("validationLink") val validationLink: String,
)
