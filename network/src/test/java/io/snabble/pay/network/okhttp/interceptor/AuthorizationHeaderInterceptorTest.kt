package io.snabble.pay.network.okhttp.interceptor

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.network.okhttp.AUTH_HEADER
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

internal class AuthorizationHeaderInterceptorTest : FreeSpec({

    fun createMockWebServer() = MockWebServer()
        .apply {
            enqueue(MockResponse().setResponseCode(200))
            start()
        }

    var server = createMockWebServer()

    val accessTokenRepo: AccessTokenProvider = mockk(relaxed = true)

    fun createRequest(header: Pair<String, String>? = null) = Request.Builder()
        .url(server.url(""))
        .apply { if (header != null) addHeader(header.first, header.second) }
        .build()

    fun sut(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationHeaderInterceptor(accessTokenRepo))
        .build()

    beforeEach {
        clearAllMocks()
        server = createMockWebServer()
        coEvery { accessTokenRepo.getAccessToken() } returns AccessToken("Bearer qwerty345")
    }

    "A request that" - {

        "has no access token and no access token is available, is sent w/o the auth header" {
            coEvery { accessTokenRepo.getAccessToken() } returns null
            val request = createRequest()

            val sut: OkHttpClient = sut()
            val response = sut.newCall(request).execute()

            response.request.headers.any { it.first == AUTH_HEADER } shouldBe false
        }

        "is missing an access token should result in a new one w/ an access token" {
            val request = createRequest()

            val sut: OkHttpClient = sut()
            val response = sut.newCall(request).execute()

            response.request.headers.shouldContainOnly(AUTH_HEADER to "Bearer qwerty345")
        }

        "has already got an access token should be overridden" {
            val request = createRequest("Authorization" to "Bearer asdfgh")

            val sut: OkHttpClient = sut()
            val response = sut.newCall(request).execute()

            response.request.headers.shouldContainOnly(AUTH_HEADER to "Bearer qwerty345")
        }
    }
})
