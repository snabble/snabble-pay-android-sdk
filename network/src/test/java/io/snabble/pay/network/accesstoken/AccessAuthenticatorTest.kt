package io.snabble.pay.network.accesstoken

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
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

    beforeTest {
        clearAllMocks()
        server = createMockWebServer()
    }


    "A request" - {

        "that receives a 401 response retries exactly one more time" {
            server.apply {
                enqueue(MockResponse().setResponseCode(401).setBody("Successful"))
                enqueue(MockResponse().setResponseCode(401).setBody("Successful"))
                enqueue(MockResponse().setResponseCode(200).setBody("Successful"))
            }
            val okHttpClient = OkHttpClient.Builder()
                .authenticator(AccessTokenAuthenticator())
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
        "that tries to authenticate the app should succeed on second try " {
            server.apply {
                enqueue(MockResponse().setResponseCode(401).setBody("Successful"))
                enqueue(MockResponse().setResponseCode(200).setBody("Successful"))
            }
            val okHttpClient = OkHttpClient.Builder()
                .authenticator(AccessTokenAuthenticator())
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
