package io.snabble.pay.network.okhttp.authenticator

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.network.okhttp.authenticator.usecase.RefreshAccessTokenUseCase
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

internal class PayAuthenticatorTest : FreeSpec({

    fun createMockWebServer(vararg responses: MockResponse) = MockWebServer()
        .apply {
            responses.forEach(::enqueue)
            start()
        }

    var server = createMockWebServer()

    val refreshToken: RefreshAccessTokenUseCase = mockk(relaxed = true)

    fun createRequest(header: Pair<String, String>? = null) = Request.Builder()
        .url(server.url(""))
        .apply { if (header != null) header(header.first, header.second) }
        .build()

    fun sut(refreshToken: RefreshAccessTokenUseCase) =
        OkHttpClient.Builder()
            .authenticator(PayAuthenticator(refreshToken))
            .build()

    beforeEach {
        clearAllMocks()
        server = createMockWebServer()
        server.dispatcher = object : Dispatcher() {

            override fun dispatch(request: RecordedRequest): MockResponse =
                MockResponse().setResponseCode(401)
        }
    }

    "A request that" - {

        "results in 401 UNAUTHORIZED retries only one more time" {
            val sut: OkHttpClient = sut(refreshToken)
            val response = sut.newCall(createRequest()).execute()

            response.responseCount.shouldBe(2)
        }

        "tries to authenticate the app succeeds on second try" {
            server = createMockWebServer(
                MockResponse().setResponseCode(401),
                MockResponse().setResponseCode(200)
            )

            val sut: OkHttpClient = sut(refreshToken)
            val response = sut.newCall(createRequest()).execute()

            response.responseCount.shouldBe(2)
            response.code.shouldBe(200)
        }

        "can't receive refreshed access token fails w/ 401 UNAUTHORIZED" {
            coEvery { refreshToken.invoke() } returns null
            val authHeader = "Authorization" to "Bearer qwerty"

            val sut: OkHttpClient = sut(refreshToken)
            val response = sut.newCall(createRequest(authHeader)).execute()

            response.request.headers.shouldContain(authHeader)
        }

        "that receives a refreshed access token should contain that access token" {
            coEvery { refreshToken.invoke() } returns AccessToken("Bearer asdfg")

            val sut: OkHttpClient = sut(refreshToken)
            val response = sut.newCall(createRequest("Authorization" to "Bearer qwerty")).execute()

            response.request.headers.shouldNotContain("Authorization" to "Bearer qwerty")
            response.request.headers.shouldContain("Authorization" to "Bearer asdfg")
        }
    }
})
