package io.snabble.pay.core.appcredentials.data.source.remote

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.network.service.register.AppRegistrationService
import io.snabble.pay.network.service.register.dto.AppCredentialsDto
import retrofit2.Response

class RemoteAppCredentialsDataSourceImplTest : FreeSpec({

    val appRegistrationService: AppRegistrationService = mockk(relaxed = true)
    val sut = RemoteAppCredentialsDataSourceImpl(appRegistrationService)

    beforeEach {
        clearAllMocks()
    }

    "The remote data source" - {

        "return app credentials on success" {
            coEvery {
                appRegistrationService.getAppCredentials().execute()
            } returns Response.success(AppCredentialsDto("test", "secret"))

            val appCredentials = sut.fetchAppCredentials()

            appCredentials.data?.id?.value.shouldBe("test")
            appCredentials.data?.secret?.value.shouldBe("secret")
        }

        "return null on error" {
            coEvery {
                appRegistrationService.getAppCredentials().execute()
            } returns Response.success(null)

            val appCredentials = sut.fetchAppCredentials()

            appCredentials.data.shouldBe(null)
        }
    }
})
