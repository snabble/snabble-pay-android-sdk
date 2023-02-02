package io.snabble.pay.core.appcredentials.data.provider

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository

class AppCredentialsProviderImplTest : FreeSpec({

    val appCredentialsRepository: AppCredentialsRepository = mockk(relaxed = true)
    val sut = AppCredentialsProviderImpl(appCredentialsRepository)

    val expectedAppCredentials: AppCredentials = mockk(relaxed = true)

    beforeEach {
        clearAllMocks()
    }

    "The app credentials provider returns" - {
        "app credentials as dto if available " {
            coEvery { appCredentialsRepository.getAppCredentials() } returns expectedAppCredentials

            val appCredentialsDto = sut.getAppCredentials()

            appCredentialsDto?.appId.shouldBe(expectedAppCredentials.id.id)
        }
        "null if not available" {
            coEvery { appCredentialsRepository.getAppCredentials() } returns null

            val appCredentialsDto = sut.getAppCredentials()

            appCredentialsDto?.shouldBe(null)
        }
    }
})
