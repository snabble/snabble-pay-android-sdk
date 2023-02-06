package io.snabble.pay.core.appcredentials.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import kotlinx.coroutines.flow.first

class LocalAppCredentialsDataSourceImplTest : FreeSpec({

    val dataStore: DataStore<Preferences> = mockk(relaxed = true)

    fun createSut() = LocalAppCredentialsDataSourceImpl(dataStore)

    beforeEach {
        clearAllMocks()
    }

    "getAppCredentials() returns" - {
        val prefs = mockk<Preferences>(relaxed = true)

        mockkStatic("kotlinx.coroutines.flow.FlowKt")
        fun setPrefsMockToReturn(appId: String?, appSecret: String?) {
            coEvery { dataStore.data.first().toPreferences() } returns prefs

            coEvery { prefs[APP_ID] } returns appId
            coEvery { prefs[APP_SECRET] } returns appSecret
        }
        "the token that's been saved to the DataStore" {
            setPrefsMockToReturn(
                appId = "testId",
                appSecret = "testSecret"
            )

            val sut = createSut()
            val appCredentials = sut.getAppCredentials()

            appCredentials shouldBe AppCredentials(
                id = AppIdentifier("testId"),
                secret = AppSecret("testSecret")
            )
        }

        "null if the" - {

            "appId is not available" {
                setPrefsMockToReturn(
                    appId = null,
                    appSecret = "testSecret"
                )

                val sut = createSut()
                val appCredentials = sut.getAppCredentials()

                appCredentials.shouldBeNull()
            }

            "appSecret is not available" {
                setPrefsMockToReturn(
                    appId = "testId",
                    appSecret = null
                )

                val sut = createSut()
                val appCredentials = sut.getAppCredentials()

                appCredentials.shouldBeNull()
            }
        }
    }

    "saveToken(TokenDto) saves the access token in a DataStore" {
        mockkStatic("androidx.datastore.preferences.core.PreferencesKt")
        val appId = slot<String>()
        val appSecret = slot<String>()
        val mutablePrefs: MutablePreferences = mockk {
            every {
                this@mockk[APP_ID] = capture(appId)
            } just runs
            every {
                this@mockk[APP_SECRET] = capture(appSecret)
            } just runs
        }
        val editTransformSlot = slot<suspend (MutablePreferences) -> Unit>()
        coEvery {
            dataStore.edit(capture(editTransformSlot))
        } coAnswers { editTransformSlot.captured.invoke(mutablePrefs); mutablePrefs }
        val appCredentials = AppCredentials(
            id = AppIdentifier("testId"),
            secret = AppSecret("testSecret")
        )

        val sut = createSut()
        sut.saveAppCredentials(appCredentials)

        appId.captured shouldBe appCredentials.id.value
        appSecret.captured shouldBe appCredentials.secret.value
    }
})
