package io.snabble.pay.core.appcredentials.domain.model

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.network.service.register.dto.AppCredentialsDto

class AppCredentialsKtTest : FreeSpec({

    val testAppCredentials: AppCredentials = mockk(relaxed = true)
    val testAppCredentialsDto: AppCredentialsDto = mockk(relaxed = true)

    beforeEach {
        every { testAppCredentialsDto.appId } returns "appId"
        every { testAppCredentials.secret.secret } returns "secret"
    }

    "mapping to app credentials" {
        val appCredentialsDto = testAppCredentialsDto.toAppCredentials()
        appCredentialsDto.id.id.shouldBe("appId")
        appCredentialsDto.secret.secret.shouldBe("")
    }

    "mapping to app credentials dto" {
        val appCredentials = testAppCredentials.toAppCredentialsDto()
        appCredentials.appId.shouldBe("")
        appCredentials.appSecret.shouldBe("secret")
    }

})
