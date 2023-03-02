package io.snabble.pay.core.internal.appcredentials.data.source

import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials

internal interface LocalAppCredentialsDataSource {

    suspend fun getAppCredentials(): AppCredentials?

    suspend fun saveAppCredentials(credentials: AppCredentials)
}
