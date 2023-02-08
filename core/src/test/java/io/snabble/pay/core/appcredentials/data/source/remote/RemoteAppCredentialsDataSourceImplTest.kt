package io.snabble.pay.core.appcredentials.data.source.remote

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.network.retrofit.Error
import io.snabble.pay.network.retrofit.Success
import io.snabble.pay.network.service.register.AppRegistrationService
import io.snabble.pay.network.service.register.dto.AppCredentialsDto

class RemoteAppCredentialsDataSourceImplTest : FreeSpec({

    val appRegistrationService: AppRegistrationService = mockk()
    val sut = RemoteAppCredentialsDataSourceImpl(appRegistrationService)

    beforeEach {
        clearAllMocks()
    }

    "The remote data source" - {

        "return app credentials on success" {
            coEvery {
                appRegistrationService.getAppCredentials()
            } returns Success(AppCredentialsDto("test", "secret"), mockk())

            val appCredentials = sut.fetchAppCredentials()

            appCredentials?.id?.value.shouldBe("test")
            appCredentials?.secret?.value.shouldBe("secret")
        }

        "return null on error" {
            coEvery {
                appRegistrationService.getAppCredentials()
            } returns Error(null, mockk())

            val appCredentials = sut.fetchAppCredentials()

            appCredentials.shouldBe(null)
        }
    }
})
