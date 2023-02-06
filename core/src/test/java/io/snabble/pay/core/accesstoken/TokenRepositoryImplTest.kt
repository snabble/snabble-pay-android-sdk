package io.snabble.pay.core.accesstoken

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.core.accesstoken.datasource.LocalTokenDataSource
import io.snabble.pay.core.accesstoken.datasource.RemoteTokenDataSource
import io.snabble.pay.core.accesstoken.datasource.TokenDto
import io.snabble.pay.core.accesstoken.datasource.isValid
import java.time.ZonedDateTime

class TokenRepositoryImplTest : FreeSpec({

    val localDataSource: LocalTokenDataSource = mockk(relaxed = true)
    val remoteDataSource: RemoteTokenDataSource = mockk()

    fun createSut() = TokenRepositoryImpl(
        localTokenDataSource = localDataSource,
        remoteTokenDataSource = remoteDataSource
    )

    beforeEach {
        clearAllMocks()
    }

    "getToken() returns" - {

        "a token (if it's available from local)" {
            val expectedToken = mockk<TokenDto>()
            coEvery { localDataSource.getToken() } returns expectedToken

            val sut = createSut()
            val token = sut.getToken()

            token shouldBe expectedToken
        }

        "if no local token is available" - {

            "a token (if it can be fetched from remote)" {
                coEvery { localDataSource.getToken() } returns null
                val expectedToken = mockk<TokenDto>()
                coEvery { remoteDataSource.getToken() } returns expectedToken

                val sut = createSut()
                val token = sut.getToken()

                token shouldBe expectedToken
            }

            "null (if it cannot be fetched from remote)" {
                coEvery { localDataSource.getToken() } returns null
                coEvery { remoteDataSource.getToken() } returns null

                val sut = createSut()
                val token = sut.getToken()

                token shouldBe null
            }
        }

        "a token even if it's expired" - {

            "from local source" {
                coEvery { localDataSource.getToken() } returns mockk {
                    every { expiryDate } returns
                            ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
                }

                val sut = createSut()
                val token = sut.getToken()

                token.shouldNotBeNull()
                token.isValid(
                    at = ZonedDateTime.parse("2024-02-17T13:37:42+00:00")
                ) shouldBe false
            }

            "from remote source" {
                coEvery { localDataSource.getToken() } returns null
                coEvery { remoteDataSource.getToken() } returns mockk {
                    every { expiryDate } returns
                            ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
                }

                val sut = createSut()
                val token = sut.getToken()

                token.shouldNotBeNull()
                token.isValid(
                    at = ZonedDateTime.parse("2024-02-17T13:37:42+00:00")
                ) shouldBe false
            }
        }
    }
})
