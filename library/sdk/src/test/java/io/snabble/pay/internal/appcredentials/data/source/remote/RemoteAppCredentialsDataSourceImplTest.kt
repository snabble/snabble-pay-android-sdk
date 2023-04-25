package io.snabble.pay.internal.appcredentials.data.source.remote

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.snabble.pay.api.retrofit.ApiError
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.service.register.AppRegistrationService
import io.snabble.pay.api.service.register.dto.AppCredentialsDto
import io.snabble.pay.sdk.internal.appcredentials.data.mapper.AppCredentialsMapper
import io.snabble.pay.sdk.internal.appcredentials.data.source.remote.CustomerKey
import io.snabble.pay.sdk.internal.appcredentials.data.source.remote.RemoteAppCredentialsDataSourceImpl

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
            } returns mockk<ApiError>()

            val sut = createSut()
            val appCredentials = sut.fetchAppCredentials()

            appCredentials.shouldBe(null)
        }
    }
})
