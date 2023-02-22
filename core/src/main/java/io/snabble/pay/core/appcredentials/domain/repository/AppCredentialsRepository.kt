package io.snabble.pay.core.appcredentials.domain.repository

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials

interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?
}
