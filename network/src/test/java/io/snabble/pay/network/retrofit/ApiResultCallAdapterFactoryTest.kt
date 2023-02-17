package io.snabble.pay.network.retrofit

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import java.lang.reflect.ParameterizedType

class ApiResultCallAdapterFactoryTest : FreeSpec({

    fun createSut() = ApiResultCallAdapterFactory.create()

    "ApiResultCallAdapterFactory.get(...) returns" - {

        "null if the return type" - {

            "is not from type Call" {
                val sut = createSut()
                val callAdapter = sut.get(
                    returnType = Class.forName("kotlin.Unit"),
                    annotations = arrayOf(),
                    retrofit = mockk(),
                )

                callAdapter.shouldBeNull()
            }

            "is from type Call but not a ParameterizedType" {
                val sut = createSut()
                val callAdapter = sut.get(
                    returnType = Class.forName("retrofit2.Call"),
                    annotations = arrayOf(),
                    retrofit = mockk(),
                )

                callAdapter.shouldBeNull()
            }

            "is from type Call<ApiResponse> but not a ParameterizedType" {
                val callMock = mockk<ParameterizedType> {
                    every { rawType } returns Class.forName("retrofit2.Call")
                    every { actualTypeArguments } returns arrayOf(
                        Class.forName("io.snabble.pay.network.retrofit.ApiResponse"),
                    )
                }

                val sut = createSut()
                val callAdapter = sut.get(
                    returnType = callMock,
                    annotations = arrayOf(),
                    retrofit = mockk(),
                )

                callAdapter.shouldBeNull()
            }
        }

        "the correct call adapter" {
            val actualRequestedType = Class.forName("kotlin.Unit")
            val apiResponseMock = mockk<ParameterizedType> {
                every { rawType } returns Class
                    .forName("io.snabble.pay.network.retrofit.ApiResponse")
                every { actualTypeArguments } returns arrayOf(actualRequestedType)
            }
            val callMock = mockk<ParameterizedType> {
                every { rawType } returns Class.forName("retrofit2.Call")
                every { actualTypeArguments } returns arrayOf(apiResponseMock)
            }

            val sut = spyk(createSut())
            val callAdapter = sut.get(
                returnType = callMock,
                annotations = arrayOf(),
                retrofit = mockk(),
            )

            verify { sut.createAdapter(actualRequestedType) }
            callAdapter.shouldNotBeNull()
        }
    }
})
