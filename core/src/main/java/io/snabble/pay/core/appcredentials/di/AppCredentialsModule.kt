package io.snabble.pay.core.appcredentials.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.snabble.pay.core.appcredentials.data.AppCredentialsRepositoryImpl
import io.snabble.pay.core.appcredentials.data.source.LocalAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.RemoteAppCredentialsDataSource
import io.snabble.pay.core.appcredentials.data.source.local.LocalRuntimeAppCredentialsDataSourceImpl
import io.snabble.pay.core.appcredentials.data.source.remote.RemoteAppCredentialsDataSourceImpl
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appCredentialsModule = module {
    factoryOf(::AppCredentialsRepositoryImpl) bind AppCredentialsRepository::class

    factoryOf(::LocalRuntimeAppCredentialsDataSourceImpl) bind LocalAppCredentialsDataSource::class

    factoryOf(::RemoteAppCredentialsDataSourceImpl) bind RemoteAppCredentialsDataSource::class

    factory { androidContext().dataStore }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
