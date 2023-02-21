package io.snabble.pay.core.token.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.TokenDto
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import kotlinx.coroutines.flow.first
import java.time.ZonedDateTime

internal class LocalTokenDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
) : LocalTokenDataSource {

    override suspend fun getToken(): TokenDto? {
        val prefs = dataStore.data.first().toPreferences()
        val token = prefs[KEY_ACCESS_TOKEN] ?: return null
        val expiryDate = prefs[KEY_EXPIRY_DATE] ?: return null
        return TokenDto(
            accessToken = AccessToken(token),
            expiryDate = ZonedDateTime.parse(expiryDate)
        )
    }

    override suspend fun saveToken(tokenDto: TokenDto) {
        dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = tokenDto.accessToken.value
            prefs[KEY_EXPIRY_DATE] = tokenDto.expiryDate.toString()
        }
    }

    internal companion object {

        internal val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        internal val KEY_EXPIRY_DATE = stringPreferencesKey("expiry_date")
    }
}
