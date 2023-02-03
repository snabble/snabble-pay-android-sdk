package io.snabble.pay.core.accesstoken.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.snabble.pay.core.accesstoken.data.source.dto.AccessTokenDto
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LocalAccessTokenDataSourceImplTest {

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testScope = TestScope()

    private var testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { testContext.preferencesDataStoreFile(DATASTORE_NAME) }
        )

    private val sut = LocalAccessTokenDataSourceImpl(dataStore = testDataStore)

    @Test
    @DisplayName("Initially the access token is null")
    fun initiallyTheAccessTokenIsNull() = testScope.runTest {
        testDataStore.edit { it.clear() }
        val accessToken = sut.getAccessToken()
        assertEquals(accessToken, null)
    }

    @Test
    @DisplayName("If an access token is saved it is returned")
    fun ifAnAccessTokenIsSavedItIsReturned() = testScope.runTest {
        testDataStore.edit { it.clear() }
        val accessTokenDto = AccessTokenDto(AccessToken("Bearer 12345"), "time")
        sut.saveAccessToken(accessTokenDto)

        val accessToken = sut.getAccessToken()

        assertEquals(accessToken, accessTokenDto)
    }

    private companion object {

        const val DATASTORE_NAME = "accessTokenTest"
    }
}
