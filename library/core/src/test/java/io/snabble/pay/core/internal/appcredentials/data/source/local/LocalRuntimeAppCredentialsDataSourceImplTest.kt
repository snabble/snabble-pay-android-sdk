package io.snabble.pay.core.internal.appcredentials.data.source.local

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.core.SnabblePayConfiguration
import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.internal.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.internal.appcredentials.domain.model.AppSecret

class LocalRuntimeAppCredentialsDataSourceImplTest : FreeSpec({

    val config: SnabblePayConfiguration = mockk(relaxed = true)

    fun createSut() = LocalRuntimeAppCredentialsDataSourceImpl(config)

    beforeEach {
        clearAllMocks()
    }

    "getAppCredentials() returns" - {

        fun setPrefsMockToReturn(appId: String?, appSecret: String?) {
            if (appId == null || appSecret == null) {
                every { config.appCredentials } returns null
            } else {
                every { config.appCredentials } returns AppCredentials(
                    id = AppIdentifier(appId),
                    secret = AppSecret(appSecret)
                )
            }
        }

        "the appCredentials that's been given by the configuration" {
            setPrefsMockToReturn(
                appId = "testId",
                appSecret = "testSecret"
            )

            val sut = createSut()
            val appCredentials = sut.getAppCredentials()

            appCredentials shouldBe AppCredentials(
                id = AppIdentifier("testId"),
                secret = AppSecret("testSecret")
            )
        }

        "null if the appCredentials are not available" {
            setPrefsMockToReturn(
                appId = null,
                appSecret = "testSecret"
            )

            val sut = createSut()
            val appCredentials = sut.getAppCredentials()

            appCredentials.shouldBeNull()
        }
    }

    "saveAppCredentials(appCredentials) saves the app credentials" {
        val appCredentials = AppCredentials(
            id = AppIdentifier("testId"),
            secret = AppSecret("testSecret")
        )

        val sut = createSut()
        sut.saveAppCredentials(appCredentials)

        sut.appCredentials shouldBe appCredentials
    }
})
