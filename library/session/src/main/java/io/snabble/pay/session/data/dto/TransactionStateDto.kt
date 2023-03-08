package io.snabble.pay.session.data.dto

import kotlinx.serialization.SerialName

internal enum class TransactionStateDto {

    @SerialName("ABORTED") ABORTED,
    @SerialName("ERRORED") ERRORED,
    @SerialName("FAILED") FAILED,
    @SerialName("PREAUTHORIZED") PREAUTHORIZED,
    @SerialName("PREAUTH_FAILED") PREAUTH_FAILED,
    @SerialName("SUCCESSFUL") SUCCESSFUL,
}
