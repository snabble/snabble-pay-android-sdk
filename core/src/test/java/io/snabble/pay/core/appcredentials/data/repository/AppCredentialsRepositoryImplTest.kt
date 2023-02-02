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

class AppCredentialsRepositoryImplTest : FreeSpec({

    val localDataSource: LocalAppCredentialsDataSource = mockk(relaxed = true)
    val remoteDataSource: RemoteAppCredentialsDataSource = mockk(relaxed = true)

    val expectedAppCredentials = AppCredentials(AppIdentifier("test"), AppSecret("secret"))

    val sut = AppCredentialsRepositoryImpl(localDataSource, remoteDataSource)

    beforeEach {
        clearAllMocks()
    }

    "the repository returns" - {

        "app credentials that have been stored locally" {
            coEvery { localDataSource.getAppCredentials() } returns expectedAppCredentials

            val appCredentials = sut.getAppCredentials()

            appCredentials.shouldBe(expectedAppCredentials)

        }
        "tries to fetch app credentials if local data is null" {

            coEvery { localDataSource.getAppCredentials() } returns null
            coEvery { remoteDataSource.fetchAppCredentials() } returns null

            sut.getAppCredentials()

            coVerify(exactly = 1) { remoteDataSource.fetchAppCredentials() }

        }
        "returns the fetched app credentials on success" {

            coEvery { localDataSource.getAppCredentials() } returns null

            coEvery { remoteDataSource.fetchAppCredentials() } returns expectedAppCredentials

            val appCredentials = sut.getAppCredentials()

            appCredentials.shouldBe(expectedAppCredentials)
        }

        "returns the null on fail" {
            coEvery { localDataSource.getAppCredentials() } returns null
            coEvery { remoteDataSource.fetchAppCredentials() } returns null

            val appCredentials = sut.getAppCredentials()

            appCredentials.shouldBe(null)
        }

        "saves the app credentials if fetched successfully" {

            coEvery { localDataSource.getAppCredentials() } returns null
            coEvery { remoteDataSource.fetchAppCredentials() } returns expectedAppCredentials

            sut.getAppCredentials()

            coVerify(exactly = 1) { localDataSource.saveAppCredentials(expectedAppCredentials) }
        }
    }

})
