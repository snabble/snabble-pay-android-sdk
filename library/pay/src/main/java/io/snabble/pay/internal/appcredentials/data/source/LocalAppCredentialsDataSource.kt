package io.snabble.pay.internal.appcredentials.data.source

import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials

internal interface LocalAppCredentialsDataSource {

    suspend fun getAppCredentials(): AppCredentials?

    suspend fun saveAppCredentials(credentials: AppCredentials)
}
