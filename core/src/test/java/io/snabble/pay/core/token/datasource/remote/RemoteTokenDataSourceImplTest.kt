package io.snabble.pay.core.token.datasource.remote

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.core.token.datasource.TokenDto
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.network.retrofit.Success
import io.snabble.pay.network.service.register.AppRegistrationService
import java.time.ZonedDateTime
import io.snabble.pay.network.service.register.dto.TokenDto as ApiTokenDto

class RemoteTokenDataSourceImplTest : FreeSpec({

    val appCredentialsRepo: AppCredentialsRepository = mockk()
    val registrationService: AppRegistrationService = mockk()

    fun createSut() = RemoteTokenDataSourceImpl(
        appCredentialsRepository = appCredentialsRepo,
        registrationService = registrationService,
    )

    beforeEach {
        clearAllMocks()
    }

    "getToken()" - {

        "uses the app credentials to request the token" {
            coEvery { appCredentialsRepo.getAppCredentials() } returns AppCredentials(
                id = AppIdentifier("id"),
                secret = AppSecret("secret")
            )
            val idSlot = slot<String>()
            val secretSlot = slot<String>()
            coEvery {
                registrationService
                    .getToken(
                        appIdentifier = capture(idSlot),
                        appSecret = capture(secretSlot)
                    )
            } returns mockk(relaxed = true)

            val sut = createSut()
            sut.getToken()

            idSlot.captured shouldBe "id"
            secretSlot.captured shouldBe "secret"
            coVerify(exactly = 1) {
                registrationService.getToken(appIdentifier = any(), appSecret = any())
            }
        }

        "returns the token if the request for the token succeeds" {
            coEvery { appCredentialsRepo.getAppCredentials() } returns AppCredentials(
                id = AppIdentifier("id"),
                secret = AppSecret("secret")
            )
            coEvery {
                registrationService
                    .getToken(
                        appIdentifier = "id",
                        appSecret = "secret"
                    )
            } returns Success(
                ApiTokenDto(
                    token = "qwerty",
                    expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00"),
                    tokenType = "Bearer"
                ),
                response = mockk()
            )
            val expectedToken = TokenDto(
                accessToken = AccessToken("Bearer qwerty"),
                expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
            )

            val sut = createSut()
            val token = sut.getToken()

            token shouldBe expectedToken
        }

        "returns null if" - {

            "app credentials are missing" {
                coEvery { appCredentialsRepo.getAppCredentials() } returns null

                val sut = createSut()

                sut.getToken().shouldBeNull()
            }

            "the request for the token fails" {
                coEvery { appCredentialsRepo.getAppCredentials() } returns AppCredentials(
                    id = AppIdentifier("id"),
                    secret = AppSecret("secret")
                )
                coEvery {
                    registrationService
                        .getToken(
                            appIdentifier = "id",
                            appSecret = "secret"
                        )
                } returns mockk()

                val sut = createSut()

                sut.getToken().shouldBeNull()
            }
        }
    }
})
