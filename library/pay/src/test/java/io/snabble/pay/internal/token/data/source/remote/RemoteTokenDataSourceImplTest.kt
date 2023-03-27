package io.snabble.pay.internal.token.data.source.remote

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.snabble.pay.api.retrofit.Success
import io.snabble.pay.api.service.register.AppRegistrationService
import io.snabble.pay.api.service.register.dto.TokenDto
import io.snabble.pay.internal.appcredentials.domain.GetAppCredentialsUseCase
import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.internal.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.internal.appcredentials.domain.model.AppSecret
import io.snabble.pay.internal.token.data.mapper.TokenMapper
import io.snabble.pay.internal.token.domain.model.Token
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import java.time.ZonedDateTime

class RemoteTokenDataSourceImplTest : FreeSpec({

    val getAppCredentials: GetAppCredentialsUseCase = mockk()
    val registrationService: AppRegistrationService = mockk()

    fun createSut() = RemoteTokenDataSourceImpl(
        getAppCredentials = getAppCredentials,
        registrationService = registrationService,
        tokenMapper = TokenMapper()
    )

    beforeEach {
        clearAllMocks()
    }

    "getToken()" - {

        "uses the app credentials to request the token" {
            coEvery { getAppCredentials() } returns AppCredentials(
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
            coEvery { getAppCredentials() } returns AppCredentials(
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
                TokenDto(
                    token = "qwerty",
                    expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00"),
                    tokenType = "Bearer"
                ),
                response = mockk()
            )
            val expectedToken = Token(
                accessToken = AccessToken("Bearer qwerty"),
                expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
            )

            val sut = createSut()
            val token = sut.getToken()

            token shouldBe expectedToken
        }

        "returns null if" - {

            "app credentials are missing" {
                coEvery { getAppCredentials() } returns null

                val sut = createSut()

                sut.getToken().shouldBeNull()
            }

            "the request for the token fails" {
                coEvery { getAppCredentials() } returns AppCredentials(
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
