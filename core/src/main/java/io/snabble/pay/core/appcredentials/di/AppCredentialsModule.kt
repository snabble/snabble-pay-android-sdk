package io.snabble.pay.core.appcredentials.di

import io.snabble.pay.core.appcredentials.data.provider.AppCredentialsProviderImpl
import io.snabble.pay.core.appcredentials.data.repository.AppCredentialsRepositoryImpl
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.local.LocalAppCredentialsDataSourceImpl
import io.snabble.pay.core.appcredentials.data.source.remote.RemoteAppCredentialsDataSourceImpl
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import io.snabble.pay.network.provider.AppCredentialsProvider
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appCredentials = module {
    factoryOf(::AppCredentialsRepositoryImpl) bind AppCredentialsRepository::class

    factoryOf(::LocalAppCredentialsDataSourceImpl) bind LocalAppCredentialsDataSource::class
    factoryOf(::RemoteAppCredentialsDataSourceImpl) bind RemoteAppCredentialsDataSource::class

    factoryOf(::AppCredentialsProviderImpl) bind AppCredentialsProvider::class
}
