package io.snabble.pay.network.accesstoken

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.network.accesstoken.authenticator.AccessTokenAuthenticator
import io.snabble.pay.network.accesstoken.authenticator.usecase.RefreshTokenUseCase
import io.snabble.pay.network.refreshtoken.responseCount
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class AccessAuthenticatorTest : FreeSpec({

    fun createMockWebServer() = MockWebServer()
        .apply {
            start()
        }

    var server = createMockWebServer()

    val refreshtoken: RefreshTokenUseCase = mockk(relaxed = true)

    beforeTest {
        clearAllMocks()
        server = createMockWebServer()
    }


    "A request" - {

        "that receives a 401 response retries only one time" {
            coEvery { refreshtoken.invoke() } returns AccessToken("")
            server.apply {
                enqueue(MockResponse().setResponseCode(401).setBody("First Error"))
                enqueue(MockResponse().setResponseCode(401).setBody("Second Error"))
                enqueue(MockResponse().setResponseCode(200).setBody("Successful"))
            }
            val okHttpClient = OkHttpClient.Builder()
                .authenticator(AccessTokenAuthenticator(refreshtoken))
                .build()

            val response = okHttpClient
                .newCall(
                    Request.Builder()
                        .url(server.url(""))
                        .build()
                )
                .execute()

            response.responseCount.shouldBe(2)
            response.code.shouldBe(401)
            response.message.shouldBe("Client Error")
        }

        "that tries to authenticate the app succeeds on second try " {
            coEvery { refreshtoken.invoke() } returns AccessToken("")

            server.apply {
                enqueue(MockResponse().setResponseCode(401).setBody("Error"))
                enqueue(MockResponse().setResponseCode(200).setBody("Successful"))
            }
            val okHttpClient = OkHttpClient.Builder()
                .authenticator(AccessTokenAuthenticator(refreshtoken))
                .build()

            val response = okHttpClient
                .newCall(
                    Request.Builder()
                        .url(server.url(""))
                        .build()
                )
                .execute()

            response.responseCount.shouldBe(2)
            response.code.shouldBe(200)
            response.message.shouldBe("OK")
        }

        "that can't receive an refresh token fails" {
            coEvery { refreshtoken.invoke() } returns null

            server.apply {
                enqueue(MockResponse().setResponseCode(401).setBody("Error"))
                enqueue(MockResponse().setResponseCode(200).setBody("Error"))
            }
            val okHttpClient = OkHttpClient.Builder()
                .authenticator(AccessTokenAuthenticator(refreshtoken))
                .build()

            val response = okHttpClient
                .newCall(
                    Request.Builder()
                        .url(server.url(""))
                        .build()
                )
                .execute()

            response.responseCount.shouldBe(1)
            response.code.shouldBe(401)
            response.message.shouldBe("Client Error")

        }

        "that can receive an refresh token succeeds" {
            coEvery { refreshtoken.invoke() } returns AccessToken("")

            server.apply {
                enqueue(MockResponse().setResponseCode(401).setBody("Error"))
                enqueue(MockResponse().setResponseCode(200).setBody("Error"))
            }
            val okHttpClient = OkHttpClient.Builder()
                .authenticator(AccessTokenAuthenticator(refreshtoken))
                .build()

            val response = okHttpClient
                .newCall(
                    Request.Builder()
                        .url(server.url(""))
                        .build()
                )
                .execute()

            response.responseCount.shouldBe(2)
            response.code.shouldBe(200)
            response.message.shouldBe("OK")

        }
    }
})
