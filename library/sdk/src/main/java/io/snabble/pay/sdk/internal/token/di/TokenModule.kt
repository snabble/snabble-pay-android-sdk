package io.snabble.pay.sdk.internal.token.di

import io.snabble.pay.network.okhttp.authenticator.GetNewAccessTokenUseCase
import io.snabble.pay.network.okhttp.interceptor.GetAccessTokenUseCase
import io.snabble.pay.sdk.internal.token.data.TokenRepositoryImpl
import io.snabble.pay.sdk.internal.token.data.mapper.TokenMapper
import io.snabble.pay.sdk.internal.token.data.source.LocalTokenDataSource
import io.snabble.pay.sdk.internal.token.data.source.RemoteTokenDataSource
import io.snabble.pay.sdk.internal.token.data.source.local.LocalRuntimeTokenDataSourceImpl
import io.snabble.pay.sdk.internal.token.data.source.remote.RemoteTokenDataSourceImpl
import io.snabble.pay.sdk.internal.token.domain.GetAccessTokenUseCaseImpl
import io.snabble.pay.sdk.internal.token.domain.GetNewAccessTokenUseCaseImpl
import io.snabble.pay.sdk.internal.token.domain.repository.TokenRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val tokenModule = module {
    singleOf(::GetAccessTokenUseCaseImpl) bind GetAccessTokenUseCase::class

    singleOf(::GetNewAccessTokenUseCaseImpl) bind GetNewAccessTokenUseCase::class

    singleOf(::TokenRepositoryImpl) bind TokenRepository::class

    factoryOf(::TokenMapper)

    factoryOf(::RemoteTokenDataSourceImpl) bind RemoteTokenDataSource::class

    factoryOf(::LocalRuntimeTokenDataSourceImpl) bind LocalTokenDataSource::class
}
