@file:Suppress("RemoveExplicitTypeArguments")

package io.snabble.pay.core.token.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.snabble.pay.core.token.GetAccessTokenUseCaseImpl
import io.snabble.pay.core.token.GetNewAccessTokenUseCaseImpl
import io.snabble.pay.core.token.TokenRepository
import io.snabble.pay.core.token.TokenRepositoryImpl
import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.RemoteTokenDataSource
import io.snabble.pay.core.token.datasource.local.LocalTokenDataSourceImpl
import io.snabble.pay.core.token.datasource.remote.RemoteTokenDataSourceImpl
import io.snabble.pay.network.okhttp.authenticator.GetNewAccessTokenUseCase
import io.snabble.pay.network.okhttp.interceptor.GetAccessTokenUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal val tokenModule = module {
    singleOf(::GetAccessTokenUseCaseImpl) bind GetAccessTokenUseCase::class

    singleOf(::GetNewAccessTokenUseCaseImpl) bind GetNewAccessTokenUseCase::class

    singleOf(::TokenRepositoryImpl) bind TokenRepository::class

    factoryOf(::RemoteTokenDataSourceImpl) bind RemoteTokenDataSource::class

    factory {
        LocalTokenDataSourceImpl(dataStore = get(named(TOKEN_DATA_STORE)))
    } bind LocalTokenDataSource::class

    factory<DataStore<Preferences>>(named(TOKEN_DATA_STORE)) { androidApplication().tokenDataStore }
}

internal val Context.tokenDataStore: DataStore<Preferences>
    by preferencesDataStore(name = "token_settings")

internal const val TOKEN_DATA_STORE = "token-data-store"
