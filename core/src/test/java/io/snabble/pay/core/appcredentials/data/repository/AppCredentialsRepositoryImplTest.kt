package io.snabble.pay.core.appcredentials.data.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import io.snabble.pay.network.response.Response

class AppCredentialsRepositoryImplTest : FreeSpec({

    val localDataSource: LocalAppCredentialsDataSource = mockk(relaxed = true)
    val remoteDataSource: RemoteAppCredentialsDataSource = mockk(relaxed = true)

    val response: okhttp3.Response = mockk(relaxed = true)

    val expectedAppCredentials = AppCredentials(AppIdentifier("test"), AppSecret("secret"))

    val sut = AppCredentialsRepositoryImpl(localDataSource, remoteDataSource)

    beforeEach {
        clearAllMocks()
    }

    "the repository" - {

        "returns app credentials that have been stored locally" {
            coEvery { localDataSource.getAppCredentials() } returns expectedAppCredentials

            val appCredentials = sut.getAppCredentials()

            appCredentials.shouldBe(expectedAppCredentials)
        }

        "tries to fetch app credentials if local data is null" {
            coEvery { localDataSource.getAppCredentials() } returns null
            coEvery { remoteDataSource.fetchAppCredentials() } returns Response(
                data = null,
                response
            )

            sut.getAppCredentials()

            coVerify(exactly = 1) { remoteDataSource.fetchAppCredentials() }
        }

        "that failed to load app credentials locally, tries to fetch an returns" - {

            "app credentials on success" {
                coEvery { localDataSource.getAppCredentials() } returns null
                coEvery { remoteDataSource.fetchAppCredentials() } returns Response(
                    data = expectedAppCredentials,
                    response
                )

                val appCredentials = sut.getAppCredentials()

                appCredentials.shouldBe(expectedAppCredentials)
            }

            "null if failed" {
                coEvery { localDataSource.getAppCredentials() } returns null
                coEvery { remoteDataSource.fetchAppCredentials() } returns Response(
                    data = null,
                    response
                )

                val appCredentials = sut.getAppCredentials()

                appCredentials.shouldBe(null)
            }
        }

        "saves the app credentials if fetched successfully" {
            coEvery { localDataSource.getAppCredentials() } returns null
            coEvery { remoteDataSource.fetchAppCredentials() } returns Response(
                data = expectedAppCredentials,
                response
            )

            sut.getAppCredentials()

            coVerify(exactly = 1) { localDataSource.saveAppCredentials(expectedAppCredentials) }
        }

        "does not saves the app credentials" - {

            "if available locally" {
                coEvery { localDataSource.getAppCredentials() } returns expectedAppCredentials

                sut.getAppCredentials()

                coVerify(exactly = 0) { localDataSource.saveAppCredentials(expectedAppCredentials) }
            }

            "if local and remote credentials are not available" {
                coEvery { localDataSource.getAppCredentials() } returns null
                coEvery { remoteDataSource.fetchAppCredentials() } returns Response(
                    data = null,
                    response
                )

                sut.getAppCredentials()

                coVerify(exactly = 0) { localDataSource.saveAppCredentials(expectedAppCredentials) }
            }
        }
    }
})
