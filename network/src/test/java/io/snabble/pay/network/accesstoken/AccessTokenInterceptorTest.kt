package io.snabble.pay.network.accesstoken

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainOnly
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class AccessTokenInterceptorTest : FreeSpec({

    fun createMockWebServer() = MockWebServer()
        .apply {
            enqueue(MockResponse().setResponseCode(200).setBody("Successful"))
            start()
        }

    var server = createMockWebServer()

    var accessTokenRepo: AccessTokenRepository = mockk()


    beforeEach {
        clearAllMocks()
        server = createMockWebServer()
        accessTokenRepo = mockk(relaxed = true) {
            coEvery { getAccessToken() } returns AccessToken("qwerty345")
        }
    }


    "A request" - {

        "that's missing an access token should result in a new one w/ an access token" {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AccessTokenInterceptor(accessTokenRepo))
                .build()

            val response = okHttpClient
                .newCall(
                    Request.Builder()
                        .url(server.url(""))
                        .build()
                )
                .execute()

            response.request.headers.shouldContainOnly("Authorization" to "Bearer qwerty345")
        }

        "that's already got an access token should be overridden" {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AccessTokenInterceptor(accessTokenRepo))
                .build()

            val response = okHttpClient
                .newCall(
                    Request.Builder()
                        .url(server.url(""))
                        .addHeader("Authorization", "Bearer asdfgh")
                        .build()
                )
                .execute()

            response.request.headers.shouldContainOnly("Authorization" to "Bearer qwerty345")
        }
    }
})
