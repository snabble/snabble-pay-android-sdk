package io.snabble.pay.internal.appcredentials.data.source

import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials

internal interface RemoteAppCredentialsDataSource {

    suspend fun fetchAppCredentials(): AppCredentials?
}
