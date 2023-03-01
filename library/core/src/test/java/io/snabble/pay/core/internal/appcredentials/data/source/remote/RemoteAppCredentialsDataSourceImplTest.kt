package io.snabble.pay.core.internal.appcredentials.data.source.remote

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.api.retrofit.Error
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.service.register.AppRegistrationService
import io.snabble.pay.api.service.register.dto.AppCredentialsDto
import io.snabble.pay.core.internal.appcredentials.data.mapper.AppCredentialsMapper

class RemoteAppCredentialsDataSourceImplTest : FreeSpec({

    val appRegistrationService: AppRegistrationService = mockk()

    fun createSut() = RemoteAppCredentialsDataSourceImpl(
        appRegistrationService,
        CustomerKey(""),
        AppCredentialsMapper()
    )

    beforeEach {
        clearAllMocks()
    }

    "The remote data source" - {

        "return app credentials on success" {
            coEvery {
                appRegistrationService.getAppCredentials(key = "")
            } returns Success(AppCredentialsDto("test", "secret"), mockk())

            val sut = createSut()
            val appCredentials = sut.fetchAppCredentials()

            appCredentials?.id?.value.shouldBe("test")
            appCredentials?.secret?.value.shouldBe("secret")
        }

        "return null on error" {
            coEvery {
                appRegistrationService.getAppCredentials(key = "")
            } returns Error(null, mockk())

            val sut = createSut()
            val appCredentials = sut.fetchAppCredentials()

            appCredentials.shouldBe(null)
        }
    }
})
