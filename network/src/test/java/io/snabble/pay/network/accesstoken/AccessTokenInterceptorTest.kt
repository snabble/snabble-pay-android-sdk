package io.snabble.pay.network.accesstoken

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.network.accesstoken.interceptor.AccessTokenInterceptor
import io.snabble.pay.network.accesstoken.interceptor.usecase.ValidateAppUseCase
import io.snabble.pay.network.accesstoken.repository.AccessToken
import io.snabble.pay.network.accesstoken.repository.AccessTokenRepository
import io.snabble.pay.network.api.data.AppCredentials
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

    val accessTokenRepo: AccessTokenRepository = mockk(relaxed = true)
    val validateApp: ValidateAppUseCase = mockk(relaxed = true)

    fun createRequest(header: Pair<String, String>? = null) = Request.Builder()
        .url(server.url(""))
        .apply {
            if (header != null) addHeader(header.first, header.second)
        }
        .build()

    fun sut(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AccessTokenInterceptor(accessTokenRepo, validateApp))
        .build()

    beforeEach {
        clearAllMocks()
        server = createMockWebServer()
        coEvery { accessTokenRepo.getAccessToken() } returns AccessToken("qwerty345")
    }

    "A request" - {

        "that's failed to receive app credentials should return without continuation" {
            coEvery { validateApp.invoke() } returns null
            val request = createRequest()

            val sut: OkHttpClient = sut()
            val response = sut.newCall(request).execute()

            response.code.shouldBe(401)
        }

        "that's missing an access token should result in a new one w/ an access token" {
            coEvery { validateApp.invoke() } returns AppCredentials("asdasd", "", "")
            val request = createRequest()

            val sut: OkHttpClient = sut()
            val response = sut.newCall(request).execute()

            response.request.headers.shouldContainOnly("Authorization" to "Bearer qwerty345")
        }

        "that's already got an access token should be overridden" {
            coEvery { validateApp.invoke() } returns AppCredentials("asdasd", "", "")
            val request = createRequest("Authorization" to "Bearer asdfgh")

            val sut: OkHttpClient = sut()
            val response = sut.newCall(request).execute()

            response.request.headers.shouldContainOnly("Authorization" to "Bearer qwerty345")
        }
    }
})
