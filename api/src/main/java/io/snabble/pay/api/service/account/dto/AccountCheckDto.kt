package io.snabble.pay.api.service.account.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountCheckDto(
    @SerialName("appUri") val appUri: String,
    @SerialName("validationLink") val validationLink: String,
)
