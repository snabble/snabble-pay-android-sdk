package io.snabble.pay.core.appcredentials.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import kotlinx.coroutines.flow.first

class LocalAppCredentialsDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : LocalAppCredentialsDataSource {

    override suspend fun getAppCredentials(): AppCredentials? {
        val prefs = dataStore.data.first().toPreferences()
        val appIdentifier = prefs[APP_ID] ?: return null
        val appSecret = prefs[APP_SECRET] ?: return null
        return AppCredentials(
            AppIdentifier(appIdentifier),
            AppSecret(appSecret),
        )
    }

    override suspend fun saveAppCredentials(credentials: AppCredentials) {
        dataStore.edit { prefs ->
            prefs[APP_ID] = credentials.id.value
            prefs[APP_SECRET] = credentials.secret.value
        }
    }
}

internal val APP_ID = stringPreferencesKey("appId")
internal val APP_SECRET = stringPreferencesKey("appSecret")
