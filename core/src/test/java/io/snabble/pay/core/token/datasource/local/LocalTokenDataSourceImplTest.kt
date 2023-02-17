package io.snabble.pay.core.token.datasource.local

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
import io.snabble.pay.core.token.datasource.TokenDto
import io.snabble.pay.core.token.datasource.local.LocalTokenDataSourceImpl.Companion.KEY_ACCESS_TOKEN
import io.snabble.pay.core.token.datasource.local.LocalTokenDataSourceImpl.Companion.KEY_EXPIRY_DATE
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import kotlinx.coroutines.flow.first
import java.time.ZonedDateTime

class LocalTokenDataSourceImplTest : FreeSpec({

    val dataStore: DataStore<Preferences> = mockk()

    fun createSut() = LocalTokenDataSourceImpl(dataStore = dataStore)

    beforeEach {
        clearAllMocks()
    }

    "getToken() returns" - {

        val prefs = mockk<Preferences>()

        mockkStatic("kotlinx.coroutines.flow.FlowKt")
        fun setPrefsMockToReturn(accessToken: String?, expiryDate: String?) {
            coEvery { dataStore.data.first().toPreferences() } returns prefs

            coEvery { prefs[KEY_ACCESS_TOKEN] } returns accessToken
            coEvery { prefs[KEY_EXPIRY_DATE] } returns expiryDate
        }

        "the token that's been saved to the DataStore" {
            setPrefsMockToReturn(
                accessToken = "Bearer qwerty",
                expiryDate = "2023-03-21T08:56:17+01:00",
            )

            val sut = createSut()
            val token = sut.getToken()

            token shouldBe TokenDto(
                accessToken = AccessToken("Bearer qwerty"),
                expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00"),
            )
        }

        "null if the" - {

            "token is not available" {
                setPrefsMockToReturn(accessToken = null, expiryDate = "2023-03-21T08:56:17+01:00")

                val sut = createSut()
                val token = sut.getToken()

                token.shouldBeNull()
            }

            "expiry date is not available" {
                setPrefsMockToReturn(accessToken = "Bearer qwerty", expiryDate = null)

                val sut = createSut()
                val token = sut.getToken()

                token.shouldBeNull()
            }
        }
    }

    "saveToken(TokenDto) saves the token in a DataStore" {
        val tokenSlot = slot<String>()
        val dateSlot = slot<String>()
        val mutablePrefs: MutablePreferences = mockk {
            every { this@mockk[KEY_ACCESS_TOKEN] = capture(tokenSlot) } just runs
            every { this@mockk[KEY_EXPIRY_DATE] = capture(dateSlot) } just runs
        }
        val editTransformSlot = slot<suspend (MutablePreferences) -> Unit>()
        mockkStatic("androidx.datastore.preferences.core.PreferencesKt")
        coEvery { dataStore.edit(capture(editTransformSlot)) } coAnswers {
            // Invoke the captured edit transform slot to actually capture the slots of interest
            editTransformSlot.captured.invoke(mutablePrefs)
            mutablePrefs
        }

        val token = TokenDto(
            accessToken = AccessToken("Bearer qwerty"),
            expiryDate = ZonedDateTime.parse("2023-03-21T08:56:17+01:00"),
        )

        val sut = createSut()
        sut.saveToken(token)

        tokenSlot.captured shouldBe token.accessToken.value
        dateSlot.captured shouldBe token.expiryDate.toString()
    }
})
