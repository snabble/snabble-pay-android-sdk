package io.snabble.pay.internal.appcredentials.data.source.local

import androidx.annotation.VisibleForTesting
import io.snabble.pay.SnabblePayConfiguration
import io.snabble.pay.internal.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials

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
