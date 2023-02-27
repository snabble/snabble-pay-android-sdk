package io.snabble.pay.core.token.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.Token
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import kotlinx.coroutines.flow.first
import java.time.ZonedDateTime

internal class LocalTokenDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : LocalTokenDataSource {

    override suspend fun getToken(): Token? {
        val prefs = dataStore.data.first().toPreferences()
        val token = prefs[KEY_ACCESS_TOKEN] ?: return null
        val expiryDate = prefs[KEY_EXPIRY_DATE] ?: return null
        return Token(
            accessToken = AccessToken(token),
            expiryDate = ZonedDateTime.parse(expiryDate)
        )
        return null
    }

    override suspend fun saveToken(token: Token) {
        dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = token.accessToken.value
            prefs[KEY_EXPIRY_DATE] = token.expiryDate.toString()
        }
    }

    internal companion object {

        internal val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        internal val KEY_EXPIRY_DATE = stringPreferencesKey("expiry_date")
    }
}
