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
    @SerialName("customer_not_found") CUSTOMER_NOT_FOUND,
    @SerialName("local_mandate") LOCAL_MANDATE,
    @SerialName("internal_error") INTERNAL_ERROR,
    @SerialName("invalid_session_state") INVALID_SESSION_STATE,
    @SerialName("invalid_transaction_state") INVALID_TRANSACTION_STATE,
    @SerialName("mandate_not_accepted") MANDATE_NOT_ACCEPTED,
    @SerialName("session_has_transaction") SESSION_HAS_TRANSACTION,
    @SerialName("session_not_found") SESSION_NOT_FOUND,
    @SerialName("session_token_expired") SESSION_TOKEN_EXPIRED,
    @SerialName("transaction_already_started") TRANSACTION_ALREADY_STARTED,
    @SerialName("transaction_not_found") TRANSACTION_NOT_FOUND,
    @SerialName("unauthorized") UNAUTHORIZED,
    @SerialName("user_not_found") USER_NOT_FOUND,
    @SerialName("token_not_found") TOKEN_NOT_FOUND,
    @SerialName("validation_error") VALIDATION_ERROR,
}

@Suppress("CyclomaticComplexMethod")
internal fun PayErrorDto?.toReason(): Reason = when (this?.reason) {
    ReasonDto.ACCOUNT_NOT_FOUND -> Reason.ACCOUNT_NOT_FOUND
    ReasonDto.CUSTOMER_NOT_FOUND -> Reason.CUSTOMER_NOT_FOUND
    ReasonDto.LOCAL_MANDATE -> Reason.LOCAL_MANDATE
    ReasonDto.INTERNAL_ERROR -> Reason.INTERNAL_ERROR
    ReasonDto.INVALID_SESSION_STATE -> Reason.INVALID_SESSION_STATE
    ReasonDto.INVALID_TRANSACTION_STATE -> Reason.INVALID_TRANSACTION_STATE
    ReasonDto.MANDATE_NOT_ACCEPTED -> Reason.MANDATE_NOT_ACCEPTED
    ReasonDto.SESSION_HAS_TRANSACTION -> Reason.SESSION_HAS_TRANSACTION
    ReasonDto.SESSION_NOT_FOUND -> Reason.SESSION_NOT_FOUND
    ReasonDto.SESSION_TOKEN_EXPIRED -> Reason.SESSION_TOKEN_EXPIRED
    ReasonDto.TOKEN_NOT_FOUND -> Reason.TOKEN_NOT_FOUND
    ReasonDto.TRANSACTION_ALREADY_STARTED -> Reason.TRANSACTION_ALREADY_STARTED
    ReasonDto.TRANSACTION_NOT_FOUND -> Reason.TRANSACTION_NOT_FOUND
    ReasonDto.UNAUTHORIZED -> Reason.UNAUTHORIZED
    ReasonDto.USER_NOT_FOUND -> Reason.USER_NOT_FOUND
    ReasonDto.VALIDATION_ERROR -> Reason.VALIDATION_ERROR
    else -> Reason.UNKNOWN
}

internal fun ErrorDto?.toPayError(): PayError = PayError(
    reason = this?.error.toReason(),
    message = this?.error?.message
)
