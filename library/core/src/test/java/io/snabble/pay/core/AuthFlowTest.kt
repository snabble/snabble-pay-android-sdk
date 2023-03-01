package io.snabble.pay.core

import android.util.Log
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.snabble.pay.core.di.modules.networkModule
import io.snabble.pay.core.di.modules.serviceModule
import io.snabble.pay.core.internal.appcredentials.data.AppCredentialsRepositoryImpl
import io.snabble.pay.core.internal.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.internal.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.internal.appcredentials.data.source.remote.CustomerKey
import io.snabble.pay.core.internal.appcredentials.data.source.remote.RemoteAppCredentialsDataSourceImpl
import io.snabble.pay.core.internal.appcredentials.di.appCredentialsModule
import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.internal.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.internal.appcredentials.domain.model.AppSecret
import io.snabble.pay.core.internal.appcredentials.domain.repository.AppCredentialsRepository
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AuthFlowTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(
            listOf(
                appCredentialsModule,
                networkModule,
                serviceModule,
                testModule,
                module {
                    single {
                        SnabblePayConfiguration.init(context = mockk(relaxed = true)) {
                            setBaseUrl(mockWebServer.url("").toString())
                            setSnabblePayKey("42")
                        }
                    }
                }
            )
        )
    )

    private val localeDataSource: LocalAppCredentialsDataSource by inject()
    private val appCredentialsRepository: AppCredentialsRepository by inject()

    private val mockWebServer = MockWebServer()
        .apply {
            enqueue(MockResponse().setResponseCode(200).setBody(credentialsBody))
            start()
        }

    init {
        mockkStatic("android.util.Log")
        every { Log.v(any(), any()) } returns 0

        "If no credentials are available locally, they are fetched from remote" {
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

    single {
        mockk<LocalAppCredentialsDataSource>(relaxed = true) {
            coEvery { getAppCredentials() } returns null
        }
    } bind LocalAppCredentialsDataSource::class

    single {
        RemoteAppCredentialsDataSourceImpl(
            appRegistrationService = get(),
            customerKey = CustomerKey(""),
            appCredentialsMapper = get()
        )
    } bind RemoteAppCredentialsDataSource::class
}
