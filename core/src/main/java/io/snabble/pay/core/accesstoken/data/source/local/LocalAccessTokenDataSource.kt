package io.snabble.pay.core.accesstoken.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.snabble.pay.core.accesstoken.data.source.dto.AccessTokenDto
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import kotlinx.coroutines.flow.first

interface LocalAccessTokenDataSource {

    suspend fun getAccessToken(): AccessTokenDto?

    suspend fun saveAccessToken(accessTokenDto: AccessTokenDto)
}

internal class LocalAccessTokenDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : LocalAccessTokenDataSource {

    override suspend fun getAccessToken(): AccessTokenDto? {
        val prefs = dataStore.data.first().toPreferences()
        val token = prefs[KEY_ACCESS_TOKEN] ?: return null
        val expiryDate = prefs[KEY_EXPIRY_DATE] ?: return null
        return AccessTokenDto(
            accessToken = AccessToken(token),
            expiryDate = expiryDate
        )
    }

    override suspend fun saveAccessToken(accessTokenDto: AccessTokenDto) {
        dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = accessTokenDto.accessToken.value
            prefs[KEY_EXPIRY_DATE] = accessTokenDto.expiryDate
        }
    }

    internal companion object {

        internal val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        internal val KEY_EXPIRY_DATE = stringPreferencesKey("expiry_date")
    }
}
