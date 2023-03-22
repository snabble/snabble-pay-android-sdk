package io.snabble.pay.api.model

import io.snabble.pay.core.PayError
import io.snabble.pay.core.Reason
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorDto(
    @SerialName("error") val error: PayErrorDto? = null,
)

@Serializable
internal data class PayErrorDto(
    @SerialName("reason") val reason: ReasonDto,
    @SerialName("message") val message: String,
)

internal enum class ReasonDto {

    @SerialName("account_not_found") ACCOUNT_NOT_FOUND,
    @SerialName("invalid_client") INVALID_CLIENT,
    @SerialName("invalid_session_state") INVALID_SESSION_STATE,
    @SerialName("mandate_not_accepted") MANDATE_NOT_ACCEPTED,
    @SerialName("session_not_found") SESSION_NOT_FOUND,
    @SerialName("unauthorized") UNAUTHORIZED,
    @SerialName("validation_error") VALIDATION_ERROR,
}

internal fun PayErrorDto?.toReason() = when (this?.reason) {
    ReasonDto.ACCOUNT_NOT_FOUND -> Reason.ACCOUNT_NOT_FOUND
    ReasonDto.INVALID_CLIENT -> Reason.INVALID_CLIENT
    ReasonDto.INVALID_SESSION_STATE -> Reason.INVALID_SESSION_STATE
    ReasonDto.MANDATE_NOT_ACCEPTED -> Reason.MANDATE_NOT_ACCEPTED
    ReasonDto.SESSION_NOT_FOUND -> Reason.SESSION_NOT_FOUND
    ReasonDto.UNAUTHORIZED -> Reason.UNAUTHORIZED
    ReasonDto.VALIDATION_ERROR -> Reason.VALIDATION_ERROR
    else -> Reason.UNKNOWN
}

internal fun ErrorDto?.toPayError(rawMessage: String?): PayError = PayError(
    reason = this?.error.toReason(),
    message = this?.error?.message ?: rawMessage
)
