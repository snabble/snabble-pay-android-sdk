package io.snabble.pay.sdk.internal.appcredentials.data.source

import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppCredentials

internal interface LocalAppCredentialsDataSource {

    suspend fun getAppCredentials(): AppCredentials?

    suspend fun saveAppCredentials(credentials: AppCredentials)
}
