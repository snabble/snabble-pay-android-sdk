package io.snabble.pay.core.appcredentials.domain.model

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.api.service.register.dto.AppCredentialsDto

class AppCredentialsKtTest : FreeSpec({

    val testAppCredentials: AppCredentials = mockk(relaxed = true)
    val testAppCredentialsDto: AppCredentialsDto = mockk(relaxed = true)

    beforeEach {
        every { testAppCredentialsDto.appId } returns "appId"
        every { testAppCredentials.secret.value } returns "secret"
    }

    "mapping to app credentials" {
        val appCredentialsDto = testAppCredentialsDto.toAppCredentials()
        appCredentialsDto.id.value.shouldBe("appId")
        appCredentialsDto.secret.value.shouldBe("")
    }

    "mapping to app credentials dto" {
        val appCredentials = testAppCredentials.toAppCredentialsDto()
        appCredentials.appId.shouldBe("")
        appCredentials.appSecret.shouldBe("secret")
    }
})
