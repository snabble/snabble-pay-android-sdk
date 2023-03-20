package io.snabble.pay.internal.appcredentials.domain.repository

import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials

internal interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?
}
