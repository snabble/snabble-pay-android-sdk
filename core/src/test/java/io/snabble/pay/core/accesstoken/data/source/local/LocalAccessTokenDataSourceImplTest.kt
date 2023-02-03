package io.snabble.pay.core.accesstoken.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.snabble.pay.core.accesstoken.data.source.dto.AccessTokenDto
import io.snabble.pay.core.accesstoken.data.source.local.LocalAccessTokenDataSourceImpl.Companion.KEY_ACCESS_TOKEN
import io.snabble.pay.core.accesstoken.data.source.local.LocalAccessTokenDataSourceImpl.Companion.KEY_EXPIRY_DATE
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import kotlinx.coroutines.flow.first

class LocalAccessTokenDataSourceImplTest : FreeSpec({

    val dataStore: DataStore<Preferences> = mockk(relaxed = true)

    fun createSut() = LocalAccessTokenDataSourceImpl(dataStore = dataStore)

    "getAccessToken() returns" - {

        val prefs = mockk<Preferences> {
            coEvery { get(KEY_ACCESS_TOKEN) } returns "qwerty"
            coEvery { get(KEY_EXPIRY_DATE) } returns "July 5 2023"
        }

        mockkStatic("kotlinx.coroutines.flow.FlowKt")
        coEvery { dataStore.data.first().toPreferences() } returns prefs


        fun setPrefsMockToReturn(accessToken: String?, expiryDate: String?) {
            coEvery { prefs[KEY_ACCESS_TOKEN] } returns accessToken
            coEvery { prefs[KEY_EXPIRY_DATE] } returns expiryDate
        }

        "the access token that's been saved to the DataStore" {
            setPrefsMockToReturn(accessToken = "qwerty", expiryDate = "July 5 2023")

            val sut = createSut()
            val token = sut.getAccessToken()

            token shouldBe AccessTokenDto(
                accessToken = AccessToken("qwerty"),
                expiryDate = "July 5 2023"
            )
        }

        "null if the" - {

            "access token is not available" {
                setPrefsMockToReturn(accessToken = null, expiryDate = "July 5 2023")

                val sut = createSut()
                val token = sut.getAccessToken()

                token.shouldBeNull()
            }

            "expiry date is not available" {
                setPrefsMockToReturn(accessToken = "qwerty", expiryDate = null)

                val sut = createSut()
                val token = sut.getAccessToken()

                token.shouldBeNull()
            }
        }
    }

    "saveAccessToken(AccessTokenDto) saves the access token in a DataStore" {
        mockkStatic("androidx.datastore.preferences.core.PreferencesKt")
        val tokenSlot = slot<String>()
        val dateSlot = slot<String>()
        val mutablePrefs: MutablePreferences = mockk {
            every { this@mockk[KEY_ACCESS_TOKEN] = capture(tokenSlot) } just runs
            every { this@mockk[KEY_EXPIRY_DATE] = capture(dateSlot) } just runs
        }
        val editTransformSlot = slot<suspend (MutablePreferences) -> Unit>()
        coEvery {
            dataStore.edit(capture(editTransformSlot))
        } coAnswers { editTransformSlot.captured.invoke(mutablePrefs); mutablePrefs }
        val accessToken = AccessTokenDto(
            accessToken = AccessToken("Bearer qwerty"),
            expiryDate = "July 5 2023"
        )

        val sut = createSut()
        sut.saveAccessToken(accessToken)

        tokenSlot.captured shouldBe accessToken.accessToken.value
        dateSlot.captured shouldBe accessToken.expiryDate
    }
})
