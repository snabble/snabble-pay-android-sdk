package io.snabble.pay.core.token.di

import io.snabble.pay.core.token.GetAccessTokenUseCaseImpl
import io.snabble.pay.core.token.GetNewAccessTokenUseCaseImpl
import io.snabble.pay.core.token.TokenRepository
import io.snabble.pay.core.token.TokenRepositoryImpl
import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.RemoteTokenDataSource
import io.snabble.pay.core.token.datasource.local.LocalRuntimeTokenDataSourceImpl
import io.snabble.pay.core.token.datasource.remote.RemoteTokenDataSourceImpl
import io.snabble.pay.network.okhttp.authenticator.GetNewAccessTokenUseCase
import io.snabble.pay.network.okhttp.interceptor.GetAccessTokenUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val tokenModule = module {
    singleOf(::GetAccessTokenUseCaseImpl) bind GetAccessTokenUseCase::class

    singleOf(::GetNewAccessTokenUseCaseImpl) bind GetNewAccessTokenUseCase::class

    singleOf(::TokenRepositoryImpl) bind TokenRepository::class

    factoryOf(::RemoteTokenDataSourceImpl) bind RemoteTokenDataSource::class

    factoryOf(::LocalRuntimeTokenDataSourceImpl) bind LocalTokenDataSource::class
}
