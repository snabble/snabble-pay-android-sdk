package io.snabble.pay.internal.appcredentials.data.mapper

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.api.service.register.dto.AppCredentialsDto
import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials

class AppCredentialsMapperTest : FreeSpec({

    val testAppCredentials: AppCredentials = mockk(relaxed = true)
    val testAppCredentialsDto: AppCredentialsDto = mockk(relaxed = true)

    fun createSut() = AppCredentialsMapper()

    beforeEach {
        every { testAppCredentialsDto.appId } returns "appId"
        every { testAppCredentials.secret.value } returns "secret"
    }

    "mapping to app credentials" {
        val sut = createSut()
        val appCredentials = sut.map(testAppCredentialsDto)

        appCredentials.id.value.shouldBe("appId")
        appCredentials.secret.value.shouldBe("")
    }
})
