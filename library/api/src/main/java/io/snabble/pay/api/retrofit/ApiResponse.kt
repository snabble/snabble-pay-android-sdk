package io.snabble.pay.api.retrofit

import io.snabble.pay.core.PayError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response

sealed class ApiResponse<out T : Any>

data class Success<T : Any>(val data: T, val response: Response<T>) : ApiResponse<T>()

data class SuccessNoContent(val response: Response<*>) : ApiResponse<Nothing>()

data class ApiError(
    val error: PayError? = null,
    val rawMessage: String? = null,
    val exception: Throwable,
) : ApiResponse<Nothing>()

@Serializable
internal data class Error(
    @SerialName("error") val error: SnabblePayError? = null,
)

@Serializable
internal data class SnabblePayError(
    @SerialName("reason") val reason: Reason,
    @SerialName("message") val message: String,
)

internal enum class Reason {

    @SerialName("account_not_found") ACCOUNT_NOT_FOUND,
    @SerialName("invalid_client") INVALID_CLIENT,
    @SerialName("invalid_session_state") INVALID_SESSION_STATE,
    @SerialName("mandate_not_accepted") MANDATE_NOT_ACCEPTED,
    @SerialName("session_not_found") SESSION_NOT_FOUND,
    @SerialName("unauthorized") UNAUTHORIZED,
    @SerialName("validation_error") VALIDATION_ERROR,
}

internal fun Error.toPayError(rawMessage: String?): PayError = with(error) {
    val reason = when (this?.reason) {
        Reason.ACCOUNT_NOT_FOUND -> io.snabble.pay.core.Reason.ACCOUNT_NOT_FOUND
        Reason.INVALID_CLIENT -> io.snabble.pay.core.Reason.INVALID_CLIENT
        Reason.INVALID_SESSION_STATE -> io.snabble.pay.core.Reason.INVALID_SESSION_STATE
        Reason.MANDATE_NOT_ACCEPTED -> io.snabble.pay.core.Reason.MANDATE_NOT_ACCEPTED
        Reason.SESSION_NOT_FOUND -> io.snabble.pay.core.Reason.SESSION_NOT_FOUND
        Reason.UNAUTHORIZED -> io.snabble.pay.core.Reason.UNAUTHORIZED
        Reason.VALIDATION_ERROR -> io.snabble.pay.core.Reason.VALIDATION_ERROR
        else -> io.snabble.pay.core.Reason.UNKNOWN
    }
    PayError(
        reason = reason,
        message = this?.message ?: rawMessage
    )
}
