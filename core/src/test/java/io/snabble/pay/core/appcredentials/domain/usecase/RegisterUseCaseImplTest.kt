package io.snabble.pay.core.appcredentials.domain.usecase

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository

class RegisterUseCaseImplTest : FreeSpec({

    val repository: AppCredentialsRepository = mockk(relaxed = true)

    val sut = RegisterUseCaseImpl(repository)

    beforeEach {
        clearAllMocks()
    }

    "A successfully registered application receives app credentials" {
        val testCredentials: AppCredentials = mockk(relaxed = true)
        coEvery { repository.getAppCredentials() } returns testCredentials

        val appCredentials = sut.invoke()

        appCredentials.shouldBe(testCredentials)
    }

    "A failed registration returns null" {
        coEvery { repository.getAppCredentials() } returns null

        val appCredentials = sut.invoke()

        appCredentials.shouldBe(null)
    }
})
