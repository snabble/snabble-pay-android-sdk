package io.snabble.pay.core.internal.appcredentials.data.source

import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials

internal interface RemoteAppCredentialsDataSource {

    suspend fun fetchAppCredentials(): AppCredentials?
}
