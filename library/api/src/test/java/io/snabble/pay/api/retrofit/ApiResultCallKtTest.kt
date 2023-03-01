package io.snabble.pay.api.retrofit

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.mockk
import retrofit2.HttpException
import retrofit2.Response

class ApiResultCallKtTest : FreeSpec({

    "Response.toApiResponse() returns" - {

        "an Error ApiResponse if the Response is not successful" {
            val response = mockk<Response<*>> {
                every { isSuccessful } returns false
                every { message() } returns "404 HTTP NOT FOUND"
                every { code() } returns 404
            }

            val sut = response.toApiResponse()

            sut.shouldBeInstanceOf<Error<*>>()
            sut.message shouldBe "404 HTTP NOT FOUND"
            sut.exception.shouldBeInstanceOf<HttpException>()
        }

        "a Success ApiResponse if the Response is successful and has a body" {
            val response = mockk<Response<*>> {
                every { isSuccessful } returns true
                every { body() } returns mockk()
            }

            val sut = response.toApiResponse()

            sut.shouldBeInstanceOf<Success<*>>()
            sut.data.shouldBeTypeOf<Any>()
        }

        "a SuccessNoContent ApiResponse if the Response is successful but is missing a body" {
            val response = mockk<Response<*>> {
                every { isSuccessful } returns true
                every { body() } returns null
            }

            val sut = response.toApiResponse()

            sut.shouldBeInstanceOf<SuccessNoContent>()
        }
    }
})
