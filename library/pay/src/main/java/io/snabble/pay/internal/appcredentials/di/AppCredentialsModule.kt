package io.snabble.pay.internal.appcredentials.di

import io.snabble.pay.internal.appcredentials.data.AppCredentialsRepositoryImpl
import io.snabble.pay.internal.appcredentials.data.mapper.AppCredentialsMapper
import io.snabble.pay.internal.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.internal.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.internal.appcredentials.data.source.local.LocalRuntimeAppCredentialsDataSourceImpl
import io.snabble.pay.internal.appcredentials.data.source.remote.RemoteAppCredentialsDataSourceImpl
import io.snabble.pay.internal.appcredentials.domain.GetAppCredentialsUseCase
import io.snabble.pay.internal.appcredentials.domain.repository.AppCredentialsRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appCredentialsModule = module {
    factory { GetAppCredentialsUseCase(get<AppCredentialsRepository>()::getAppCredentials) }

    singleOf(::AppCredentialsRepositoryImpl) bind AppCredentialsRepository::class

    factoryOf(::LocalRuntimeAppCredentialsDataSourceImpl) bind LocalAppCredentialsDataSource::class

    factoryOf(::RemoteAppCredentialsDataSourceImpl) bind RemoteAppCredentialsDataSource::class

    factoryOf(::AppCredentialsMapper)
}
