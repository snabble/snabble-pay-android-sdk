package io.snabble.pay.api.retrofit

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.api.model.ReasonDto
import io.snabble.pay.core.PayError
import io.snabble.pay.core.Reason
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response

class ApiResultCallKtTest : FreeSpec({

    "Response.toSuccessResponse() returns" - {

        "a Success ApiResponse if the Response has a body" {
            val response = mockk<Response<*>> {
                every { body() } returns mockk()
            }

            val sut = response.toSuccessResponse()

            sut.shouldBeInstanceOf<Success<*>>()
            sut.data.shouldBeTypeOf<Any>()
        }

        "a SuccessNoContent ApiResponse if the Response is missing a body" {
            val response = mockk<Response<*>> {
                every { body() } returns null
            }

            val sut = response.toSuccessResponse()

            sut.shouldBeInstanceOf<SuccessNoContent>()
        }
    }

    "Response.toErrorResponse()" - {

        "returns the ApiResponse" - {

            "of type ApiError" {
                val response = mockk<Response<*>> {
                    every { message() } returns "NOT FOUND"
                    every { code() } returns 404
                    every { errorBody() } returns null
                }

                val sut = response.toErrorResponse(json = mockk())

                sut.shouldBeInstanceOf<ApiError>()
            }

            "ApiError" - {

                "w/ an HttpException containing code and message" {
                    val response = mockk<Response<*>> {
                        every { message() } returns "NOT FOUND"
                        every { code() } returns 404
                        every { errorBody() } returns null
                    }

                    val sut = response.toErrorResponse(json = mockk())

                    val exception = sut.exception
                    exception.shouldBeInstanceOf<HttpException>()
                    exception.code() shouldBe 404
                    exception.message shouldBe "HTTP 404 NOT FOUND"
                }

                "w/ with the reason UNKNOWN if the json cannot be parsed" {
                    val response = mockk<Response<*>> {
                        every { code() } returns 400
                        every { message() } returns "Unauthorized"
                        every { errorBody()?.string() } returns
                            """{ "error": { "reason": "", "message": "Unknown error occurred" } }"""
                    }

                    val sut = response.toErrorResponse(json = Json)

                    sut.error.reason shouldBe Reason.UNKNOWN
                }

                "w/ the rawMessage being the errorBody() if can be parsed" {
                    val response = mockk<Response<*>> {
                        every { code() } returns 400
                        every { message() } returns "Unauthorized"
                        every { errorBody()?.string() } returns
                            """{ "error": { "reason": "unauthorized", "message": "" } }"""
                    }

                    val sut = response.toErrorResponse(json = Json)

                    sut.rawMessage shouldBe """{ "error": { "reason": "unauthorized", "message": "" } }"""
                    sut.error shouldBe PayError(
                        reason = Reason.UNAUTHORIZED,
                        message = ""
                    )
                }

                "w/ the rawMessage being the errorBody() if it cannot be parsed" {
                    val errorBody = """Hello World"""
                    val response = mockk<Response<*>> {
                        every { code() } returns 400
                        every { message() } returns "Unauthorized"
                        every { errorBody()?.string() } returns errorBody
                    }

                    val sut = response.toErrorResponse(json = Json)

                    sut.rawMessage shouldBe errorBody
                }
            }
        }

        "decodes the enum ReasonDto value" - {

            ReasonDto.values().size shouldBe 16

            fun mockResponse(code: Int, reason: String, message: String = "") =
                mockk<Response<*>> {
                    every { code() } returns code
                    every { message() } returns "Unauthorized"
                    every { errorBody()?.string() } returns
                        "{\"error\":{\"reason\":\"$reason\",\"message\":\"$message\"}}"
                }

            "ACCOUNT_NOT_FOUND" {
                val sut = mockResponse(
                    code = 404,
                    reason = "account_not_found"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.ACCOUNT_NOT_FOUND
            }

            "CUSTOMER_NOT_FOUND" {
                val sut = mockResponse(
                    code = 404,
                    reason = "customer_not_found"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.CUSTOMER_NOT_FOUND
            }

            "INTERNAL_ERROR" {
                val sut = mockResponse(
                    code = 500,
                    reason = "internal_error"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.INTERNAL_ERROR
            }

            "INVALID_SESSION_STATE" {
                val sut = mockResponse(
                    code = 400,
                    reason = "invalid_session_state"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.INVALID_SESSION_STATE
            }

            "INVALID_TRANSACTION_STATE" {
                val sut = mockResponse(
                    code = 400,
                    reason = "invalid_transaction_state"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.INVALID_TRANSACTION_STATE
            }

            "MANDATE_NOT_ACCEPTED" {
                val sut = mockResponse(
                    code = 400,
                    reason = "mandate_not_accepted"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.MANDATE_NOT_ACCEPTED
            }

            "SESSION_HAS_TRANSACTION" {
                val sut = mockResponse(
                    code = 400,
                    reason = "session_has_transaction"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.SESSION_HAS_TRANSACTION
            }

            "SESSION_NOT_FOUND" {
                val sut = mockResponse(
                    code = 404,
                    reason = "session_not_found"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.SESSION_NOT_FOUND
            }

            "SESSION_TOKEN_EXPIRED" {
                val sut = mockResponse(
                    code = 400,
                    reason = "session_token_expired"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.SESSION_TOKEN_EXPIRED
            }

            "TOKEN_NOT_FOUND" {
                val sut = mockResponse(
                    code = 404,
                    reason = "token_not_found"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.TOKEN_NOT_FOUND
            }

            "TRANSACTION_ALREADY_STARTED" {
                val sut = mockResponse(
                    code = 400,
                    reason = "transaction_already_started"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.TRANSACTION_ALREADY_STARTED
            }

            "TRANSACTION_NOT_FOUND" {
                val sut = mockResponse(
                    code = 404,
                    reason = "transaction_not_found"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.TRANSACTION_NOT_FOUND
            }

            "UNAUTHORIZED" {
                val sut = mockResponse(
                    code = 401,
                    reason = "unauthorized"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.UNAUTHORIZED
            }

            "USER_NOT_FOUND" {
                val sut = mockResponse(
                    code = 404,
                    reason = "user_not_found"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.USER_NOT_FOUND
            }

            "VALIDATION_ERROR" {
                val sut = mockResponse(
                    code = 400,
                    reason = "validation_error"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.VALIDATION_ERROR
            }

            "that's not known to UNKNOWN" {
                val sut = mockResponse(
                    code = 400,
                    reason = "some_new"
                ).toErrorResponse(json = Json)

                sut.error.reason shouldBe Reason.UNKNOWN
            }
        }
    }
})
