package io.snabble.pay.core.appcredentials.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class LocalAppCredentialsDataSourceImplTest {

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testAppCredentials = AppCredentials(AppIdentifier("test"), AppSecret("secret"))

    private val testScope = TestScope()

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
        )

    private val sut = LocalAppCredentialsDataSourceImpl(testDataStore)

    @Test
    @DisplayName("localDataSource successfully loads the app credentials")
    fun localDataSource_testLoadCredentials() = testScope.runTest {
        testDataStore.edit { it.clear() }

        testDataStore.edit { prefs ->
            prefs[APP_ID] = testAppCredentials.id.id
            prefs[APP_SECRET] = testAppCredentials.secret.secret
        }

        val appCredentials = sut.getAppCredentials()

        assertEquals(testAppCredentials, appCredentials)
    }

    @Test
    @DisplayName("localDataSource successfully saves the app credentials")
    fun localDataSource_testSaveCredentials() = testScope.runTest {
        testDataStore.edit { it.clear() }

        sut.saveAppCredentials(testAppCredentials)

        assertEquals(testAppCredentials.id.id, testDataStore.data.first()[APP_ID])
        assertEquals(testAppCredentials.secret.secret, testDataStore.data.first()[APP_SECRET])
    }
}

private const val TEST_DATASTORE_NAME: String = "test_datastore"
