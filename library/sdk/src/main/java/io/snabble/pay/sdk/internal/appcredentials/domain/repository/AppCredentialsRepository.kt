package io.snabble.pay.sdk.internal.appcredentials.domain.repository

import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppCredentials

internal interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?
}
