package io.snabble.pay.sdk.internal.appcredentials.data.source

import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppCredentials

internal interface RemoteAppCredentialsDataSource {

    suspend fun fetchAppCredentials(): AppCredentials?
}
