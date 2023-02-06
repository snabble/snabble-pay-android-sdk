package io.snabble.pay.core

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.snabble.pay.core.appcredentials.data.AppCredentialsRepositoryImpl
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.remote.RemoteAppCredentialsDataSourceImpl
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.core.di.modules.services
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AuthFlowTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(listOf(services, testModule)),
    )

    private val localeDataSource: LocalAppCredentialsDataSource by inject()
    private val appCredentialsRepository: AppCredentialsRepository by inject()

    private val mockWebServer = MockWebServer()
        .apply {
            enqueue(MockResponse().setResponseCode(200).setBody(credentialsBody))
            start()
        }

    init {
        beforeSpec {
            SnabblePay.baseUrl = mockWebServer.url("").toString()
        }

        "fun fun fun" {
            val slot = slot<AppCredentials>()
            val credentials = appCredentialsRepository.getAppCredentials()
            val expectedCredentials = AppCredentials(AppIdentifier("qwerty"), AppSecret("123456"))
            credentials shouldBe expectedCredentials

            coVerify(exactly = 1) { localeDataSource.saveAppCredentials(capture(slot)) }
            val captured = slot.captured
            captured shouldBe expectedCredentials
        }
    }

    private companion object {

        const val credentialsBody = """
            {
                "appIdentifier": "qwerty",
                "appSecret": "123456"
            }
            """
    }
}

val testModule = module {

    singleOf(::AppCredentialsRepositoryImpl) bind AppCredentialsRepository::class

    single<LocalAppCredentialsDataSource> {
        mockk(relaxed = true) {
            coEvery { getAppCredentials() } returns null
        }
    }

    singleOf(::RemoteAppCredentialsDataSourceImpl) bind RemoteAppCredentialsDataSource::class
}
