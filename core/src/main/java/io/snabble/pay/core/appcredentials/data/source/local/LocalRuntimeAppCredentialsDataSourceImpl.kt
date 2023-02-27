package io.snabble.pay.core.appcredentials.data.source.local

import io.snabble.pay.core.SnabblePayConfiguration
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials

class LocalRuntimeAppCredentialsDataSourceImpl(
    config: SnabblePayConfiguration,
) : LocalAppCredentialsDataSource {

    private var appCredentials: AppCredentials? = config.appCredentials

    override suspend fun getAppCredentials(): AppCredentials? = appCredentials

    override suspend fun saveAppCredentials(credentials: AppCredentials) {
        appCredentials = credentials
    }
}
