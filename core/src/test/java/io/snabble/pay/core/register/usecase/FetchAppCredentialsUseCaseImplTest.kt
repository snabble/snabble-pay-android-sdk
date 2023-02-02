package io.snabble.pay.core.register.usecase

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.mockk
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import retrofit2.Retrofit

class FetchAppCredentialsUseCaseImplTest : FreeSpec({

    fun createMockWebServer() = MockWebServer().apply {
        start()
    }

    var server: MockWebServer = createMockWebServer()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(server.url("")).build()

    val sut: FetchAppCredentialsUseCaseImpl = mockk()


    beforeEach {
        clearAllMocks()
        server = createMockWebServer()
        server.dispatcher = object : Dispatcher() {

            override fun dispatch(request: RecordedRequest): MockResponse {
                request.path?.let { path ->
                    if (path.endsWith("/app/register")) {
                        return MockResponse().setResponseCode(200).setBody(
                            """{
  "appIdentifier": "test",
  "appSecret": "secret",
}"""
                        )
                    }
                }
                return MockResponse().setResponseCode(401)
            }
        }
    }

    "A successfully fetch on the registration endpoint returns app credentials" {

        val appCredentials = sut()

        appCredentials?.id.shouldBe("test")
    }
})
