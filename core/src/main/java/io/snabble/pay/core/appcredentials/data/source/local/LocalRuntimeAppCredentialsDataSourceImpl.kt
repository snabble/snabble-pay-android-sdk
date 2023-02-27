package io.snabble.pay.core.appcredentials.data.source.local

import androidx.annotation.VisibleForTesting
import io.snabble.pay.core.SnabblePayConfiguration
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.domain.model.AppCredentials

internal class LocalRuntimeAppCredentialsDataSourceImpl(
    config: SnabblePayConfiguration,
) : LocalAppCredentialsDataSource {

    @VisibleForTesting
    internal var appCredentials: AppCredentials? = config.appCredentials

    override suspend fun getAppCredentials(): AppCredentials? = appCredentials

    override suspend fun saveAppCredentials(credentials: AppCredentials) {
        appCredentials = credentials
    }
}
