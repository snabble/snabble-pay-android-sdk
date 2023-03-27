package io.snabble.pay.api.retrofit

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiResultCallTest : FreeSpec({

    val call = mockk<Call<Any>>(relaxed = true)

    fun createSut() = ApiResultCall(call, json = mockk(relaxed = true))

    beforeEach {
        clearAllMocks()
    }

    "enqueue()" - {

        "uses the call w/ a new callback" {
            val sut = createSut()
            sut.enqueue(mockk(relaxed = true))

            verify { call.enqueue(any()) }
        }

        "calls onResponse() on the given callback on onResponse from the call" {
            mockkStatic("io.snabble.pay.api.retrofit.ApiResultCallKt")
            every { any<Response<*>>().toSuccessResponse() } returns mockk()
            val callbackSlot = slot<Callback<Any>>()
            every { call.enqueue(capture(callbackSlot)) } answers {
                callbackSlot.captured.onResponse(
                    call,
                    mockk { every { isSuccessful } returns true }
                )
            }
            val callback = mockk<Callback<ApiResponse<Any>>>(relaxed = true)

            val sut = createSut()
            sut.enqueue(callback)

            verify(exactly = 1) { callback.onResponse(any(), any()) }
        }

        "calls onResponse() on the given callback on onFailure from the call" {
            mockkStatic("io.snabble.pay.api.retrofit.ApiResultCallKt")
            val callbackSlot = slot<Callback<Any>>()
            every { call.enqueue(capture(callbackSlot)) } answers {
                callbackSlot.captured.onFailure(call, mockk(relaxed = true))
            }
            val callback = mockk<Callback<ApiResponse<Any>>>(relaxed = true)

            val sut = createSut()
            sut.enqueue(callback)

            verify(exactly = 1) { callback.onResponse(any(), any()) }
            verify(exactly = 0) { callback.onFailure(any(), any()) }
        }

        "delivers through the callback" - {

            "a Success ApiResponse" {
                mockkStatic("io.snabble.pay.api.retrofit.ApiResultCallKt")
                every { any<Response<*>>().toSuccessResponse() } returns mockk<Success<*>>()
                val callbackSlot = slot<Callback<Any>>()
                every { call.enqueue(capture(callbackSlot)) } answers {
                    callbackSlot.captured.onResponse(
                        call,
                        mockk { every { isSuccessful } returns true }
                    )
                }
                val responseSlot = slot<Response<ApiResponse<*>>>()
                val callback = mockk<Callback<ApiResponse<Any>>>(relaxed = true) {
                    every { onResponse(any(), capture(responseSlot)) } just runs
                }

                val sut = createSut()
                sut.enqueue(callback)

                val apiResponse = responseSlot.captured.body()
                apiResponse.shouldBeInstanceOf<Success<*>>()
            }

            "a SuccessNoContent ApiResponse" {
                mockkStatic("io.snabble.pay.api.retrofit.ApiResultCallKt")
                every { any<Response<*>>().toSuccessResponse() } returns mockk<SuccessNoContent>()
                val callbackSlot = slot<Callback<Any>>()
                every { call.enqueue(capture(callbackSlot)) } answers {
                    callbackSlot.captured.onResponse(
                        call,
                        mockk { every { isSuccessful } returns true }
                    )
                }
                val responseSlot = slot<Response<ApiResponse<*>>>()
                val callback = mockk<Callback<ApiResponse<Any>>>(relaxed = true) {
                    every { onResponse(any(), capture(responseSlot)) } just runs
                }

                val sut = createSut()
                sut.enqueue(callback)

                val apiResponse = responseSlot.captured.body()
                apiResponse.shouldBeInstanceOf<SuccessNoContent>()
            }

            "an error ApiResponse " - {

                "if the response is not successful" {
                    mockkStatic("io.snabble.pay.api.retrofit.ApiResultCallKt")
                    every { any<Response<*>>().toErrorResponse(json = any()) } returns mockk()
                    val callbackSlot = slot<Callback<Any>>()
                    every { call.enqueue(capture(callbackSlot)) } answers {
                        callbackSlot.captured.onResponse(
                            call,
                            mockk { every { isSuccessful } returns false }
                        )
                    }
                    val responseSlot = slot<Response<ApiResponse<*>>>()
                    val callback = mockk<Callback<ApiResponse<Any>>>(relaxed = true) {
                        every { onResponse(any(), capture(responseSlot)) } just runs
                    }

                    val sut = createSut()
                    sut.enqueue(callback)

                    val apiResponse = responseSlot.captured.body()
                    apiResponse.shouldBeInstanceOf<ApiError>()
                }

                "w/ the message" - {

                    "'No internet connection' if it's an IOException" {
                        val callbackSlot = slot<Callback<Any>>()
                        every { call.enqueue(capture(callbackSlot)) } answers {
                            callbackSlot.captured.onFailure(
                                call,
                                mockk<IOException>()
                            )
                        }
                        val responseSlot = slot<Response<ApiResponse<*>>>()
                        val callback = mockk<Callback<ApiResponse<Any>>>(relaxed = true) {
                            every { onResponse(any(), capture(responseSlot)) } just runs
                        }

                        val sut = createSut()
                        sut.enqueue(callback)

                        val apiResponse = responseSlot.captured.body()
                        apiResponse.shouldBeInstanceOf<ApiError>()
                        apiResponse.rawMessage shouldBe "No internet connection"
                    }

                    "from the Exception that's been passed" {
                        val callbackSlot = slot<Callback<Any>>()
                        every { call.enqueue(capture(callbackSlot)) } answers {
                            callbackSlot.captured.onFailure(
                                call,
                                mockk { every { localizedMessage } returns "JSON parser exception" }
                            )
                        }
                        val responseSlot = slot<Response<ApiResponse<*>>>()
                        val callback = mockk<Callback<ApiResponse<Any>>>(relaxed = true) {
                            every { onResponse(any(), capture(responseSlot)) } just runs
                        }

                        val sut = createSut()
                        sut.enqueue(callback)

                        val apiResponse = responseSlot.captured.body()
                        apiResponse.shouldBeInstanceOf<ApiError>()
                        apiResponse.rawMessage shouldBe "JSON parser exception"
                    }
                }
            }
        }
    }

    "simple call delegation for" - {

        "clone() results in a new ApiResultCall object" {
            every { call.clone() } returns mockk()
            val sut = createSut()
            val clone = sut.clone()

            clone shouldNotBe sut
            verify(exactly = 1) { call.clone() }
        }

        "isExecuted()" - {

            "returns true if call returns true" {
                every { call.isExecuted } returns true

                val result = createSut().isExecuted

                verify(exactly = 1) { call.isExecuted }
                result shouldBe true
            }

            "returns false if call returns false" {
                every { call.isExecuted } returns false

                val result = createSut().isExecuted

                verify(exactly = 1) { call.isExecuted }
                result shouldBe false
            }
        }

        "cancel()" {
            createSut().cancel()

            verify(exactly = 1) { call.cancel() }
        }

        "isCanceled()" - {

            "returns true if call returns true" {
                every { call.isCanceled } returns true

                val result = createSut().isCanceled

                verify(exactly = 1) { call.isCanceled }
                result shouldBe true
            }

            "returns false if call returns false" {
                every { call.isCanceled } returns false

                val result = createSut().isCanceled

                verify(exactly = 1) { call.isCanceled }
                result shouldBe false
            }
        }

        "request() returns the request from the call" {
            val requestMock = mockk<Request>()
            every { call.request() } returns requestMock
            val sut = createSut()
            val request = sut.request()

            request shouldBe requestMock
            verify(exactly = 1) { call.request() }
        }

        "timeout() returns the timeout from the call" {
            val timeoutMock = mockk<Timeout>()
            every { call.timeout() } returns timeoutMock
            val sut = createSut()
            val timeout = sut.timeout()

            timeout shouldBe timeoutMock
            verify(exactly = 1) { call.timeout() }
        }
    }

    "calling execute() throws a NotImplementedError" {
        val sut = createSut()

        shouldThrow<NotImplementedError> { sut.execute() }
    }
})
