package io.snabble.pay.network.accesstoken.repository

import io.snabble.pay.network.api.data.AppCredentials

interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?

    suspend fun saveAppCredentials(appCredentials: AppCredentials)
}
