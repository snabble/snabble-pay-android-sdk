package io.snabble.pay.core.appcredentials.data.source

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials

interface LocalAppCredentialsDataSource {

    suspend fun getAppCredentials(): AppCredentials?

    suspend fun saveAppCredentials(credentials: AppCredentials)
}
