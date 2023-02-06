package io.snabble.pay.core.appcredentials.data.source

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.network.response.Response

interface RemoteAppCredentialsDataSource {

    suspend fun fetchAppCredentials(): Response<AppCredentials?>
}
