package io.snabble.pay.core

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldNotBe
import io.snabble.pay.core.di.modules.services
import okhttp3.mockwebserver.MockWebServer
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit

class AuthFlowTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(KoinExtension(services))

    private val retrofit: Retrofit by inject()

    private val mockWebServer = MockWebServer().apply { start() }

    init {
        beforeSpec {
            SnabblePay.baseUrl = mockWebServer.url("").toString()
        }

        "fun fun fun" {
            retrofit.baseUrl().toString() shouldNotBe "https://example.com/"
        }

        "fun fun fun fun" {
            retrofit.baseUrl().toString() shouldNotBe "https://example.com/"
        }
    }
}
