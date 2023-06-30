package io.snabble.pay.session.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class TransactionStateDto {

    @SerialName("ABORTED") ABORTED,
    @SerialName("ERRORED") ERRORED,
    @SerialName("FAILED") FAILED,
    @SerialName("PENDING") PENDING,
    @SerialName("PREAUTHORIZED") PREAUTHORIZED,
    @SerialName("PREAUTH_FAILED") PREAUTH_FAILED,
    @SerialName("SUCCESSFUL") SUCCESSFUL,
    @SerialName("PREAUTHORIZATION_SUCCESSFUL") PREAUTHORIZATION_SUCCESSFUL,
}
