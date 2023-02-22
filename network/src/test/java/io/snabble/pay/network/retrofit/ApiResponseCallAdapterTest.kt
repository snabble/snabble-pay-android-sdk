package io.snabble.pay.network.retrofit

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.mockk
import retrofit2.Call
import java.lang.reflect.Type

class ApiResponseCallAdapterTest : FreeSpec({

    fun createSut(type: Type) = ApiResponseCallAdapter(type)

    "responseType() returns the type it's been created with" {
        val type = mockk<Type>()

        val sut = createSut(type)

        sut.responseType() shouldBe type
    }

    "adapt(Call) returns an ApiResultCall" {
        val type = mockk<Type>()
        val call = mockk<Call<Type>>()

        val sut = createSut(type)
        val adaptedCall = sut.adapt(call)

        adaptedCall.shouldBeInstanceOf<ApiResultCall<ApiResponse<Type>>>()
    }
})
