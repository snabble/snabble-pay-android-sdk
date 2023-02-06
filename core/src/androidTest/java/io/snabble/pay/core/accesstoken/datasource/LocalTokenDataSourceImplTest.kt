package io.snabble.pay.core.accesstoken.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import java.time.ZonedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LocalTokenDataSourceImplTest {

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testScope = TestScope()

    private var testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { testContext.preferencesDataStoreFile(DATASTORE_NAME) }
        )

    private val sut = LocalTokenDataSourceImpl(dataStore = testDataStore)

    @Test
    @DisplayName("Initially the access token is null")
    fun initiallyTheAccessTokenIsNull() = testScope.runTest {
        testDataStore.edit { it.clear() }
        val accessToken = sut.getToken()
        assertEquals(accessToken, null)
    }

    @Test
    @DisplayName("If an access token is saved it is returned")
    fun ifAnAccessTokenIsSavedItIsReturned() = testScope.runTest {
        testDataStore.edit { it.clear() }
        val expectedToken = TokenDto(
            AccessToken("Bearer 12345"),
            ZonedDateTime.parse("2023-03-21T08:56:17+01:00")
        )
        sut.saveToken(expectedToken)

        val token = sut.getToken()

        assertEquals(token, expectedToken)
    }

    private companion object {

        const val DATASTORE_NAME = "accessTokenTest"
    }
}
