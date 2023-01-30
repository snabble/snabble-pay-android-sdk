package io.snabble.pay.network.appcredentials

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.network.accesstoken.interceptor.usecase.FetchAppCredentialsUseCase
import io.snabble.pay.network.accesstoken.interceptor.usecase.ValidateAppUseCaseImpl
import io.snabble.pay.network.accesstoken.repository.AppCredentialsRepository
import io.snabble.pay.network.api.data.AppCredentials

class ValidateAppUseCaseTest : FreeSpec({

    val appCredentialsRepository: AppCredentialsRepository = mockk(relaxed = true)
    val fetchAppCredentials: FetchAppCredentialsUseCase = mockk(relaxed = true)

    val sut = ValidateAppUseCaseImpl(appCredentialsRepository, fetchAppCredentials)

    beforeEach {
        clearAllMocks()
    }

    "A validation succeeds if: " - {

        "it can load the credentials locally" {
            val expected = AppCredentials(
                "", "", ""
            )
            coEvery { appCredentialsRepository.getAppCredentials() } returns expected

            val appCredentials = sut()

            appCredentials shouldBe expected
        }

        "local load fails but fetching is successfully" {
            val expected = AppCredentials(
                "", "", ""
            )
            coEvery { appCredentialsRepository.getAppCredentials() } returns null
            coEvery { fetchAppCredentials.invoke() } returns expected
            val appCredentials = sut()

            appCredentials shouldBe expected
        }
    }

    "A validation fails if:" - {

        "local load and fetching fails " {
            coEvery { appCredentialsRepository.getAppCredentials() } returns null
            coEvery { fetchAppCredentials.invoke() } returns null
            val appCredentials = sut()

            appCredentials shouldBe null
        }
    }
})
