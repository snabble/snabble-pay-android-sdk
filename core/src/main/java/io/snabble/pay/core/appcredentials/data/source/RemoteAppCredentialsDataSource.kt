package io.snabble.pay.core.appcredentials.data.source

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials

interface RemoteAppCredentialsDataSource {

    suspend fun fetchAppCredentials(): AppCredentials?
}
