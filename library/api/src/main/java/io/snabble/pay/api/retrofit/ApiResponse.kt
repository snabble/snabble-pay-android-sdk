package io.snabble.pay.api.retrofit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response

sealed class ApiResponse<out T : Any>

data class Success<T : Any>(val data: T, val response: Response<T>) : ApiResponse<T>()

data class SuccessNoContent(val response: Response<*>) : ApiResponse<Nothing>()

data class ApiError(
    val error: SnabblePayError? = null,
    val rawMessage: String? = null,
    val exception: Throwable,
) : ApiResponse<Nothing>()

@Serializable
data class Error(
    @SerialName("error") val error: SnabblePayError? = null,
)

@Serializable
data class SnabblePayError(
    @SerialName("reason") val reason: Reason,
    @SerialName("message") val message: String,
)

enum class Reason {

    @SerialName("account_not_found") ACCOUNT_NOT_FOUND,
    @SerialName("invalid_client") INVALID_CLIENT,
    @SerialName("invalid_session_state") INVALID_SESSION_STATE,
    @SerialName("mandate_not_accepted") MANDATE_NOT_ACCEPTED,
    @SerialName("session_not_found") SESSION_NOT_FOUND,
    @SerialName("unauthorized") UNAUTHORIZED,
    @SerialName("validation_error") VALIDATION_ERROR,
}
