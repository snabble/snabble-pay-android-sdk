package io.snabble.pay.core.internal.appcredentials.domain.repository

import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials

internal interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?
}
