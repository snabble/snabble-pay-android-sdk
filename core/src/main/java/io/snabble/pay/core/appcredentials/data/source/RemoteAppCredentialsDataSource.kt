package io.snabble.pay.core.appcredentials.data.source

import io.snabble.pay.network.AppCredentials

interface RemoteAppCredentialsDataSource {

    suspend fun fetchAppCredentials(): AppCredentials?
}
