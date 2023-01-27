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


    beforeEach {
        clearAllMocks()
        server = createMockWebServer()
        coEvery { accessTokenRepo.getAccessToken() } returns AccessToken("qwerty345")
    }


    "A request" - {

        "that's failed to receive app credentials should return without continuation" {

            coEvery { validateApp.invoke() } returns null

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AccessTokenInterceptor(accessTokenRepo, validateApp))
                .build()

            val response = okHttpClient
                .newCall(
                    Request.Builder()
                        .url(server.url(""))
                        .build()
                )
                .execute()

            println(response.message)

            response.code.shouldBe(401)
            clearAllMocks()
        }

        "that's missing an access token should result in a new one w/ an access token" {

            coEvery { validateApp.invoke() } returns AppCredentials("asdasd", "", "")

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AccessTokenInterceptor(accessTokenRepo, validateApp))
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

            coEvery { validateApp.invoke() } returns AppCredentials("asdasd", "", "")

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AccessTokenInterceptor(accessTokenRepo, validateApp))
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
