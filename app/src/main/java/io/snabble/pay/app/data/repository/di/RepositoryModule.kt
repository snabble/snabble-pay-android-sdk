package io.snabble.pay.app.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.data.repository.AccountRepositoryImpl
import io.snabble.pay.app.data.repository.remotedatasource.RemoteDataSourceImpl
import io.snabble.pay.app.data.repository.localdatasource.AccountsLocalDataSource
import io.snabble.pay.app.data.repository.localdatasource.AccountsLocalDataSourceImpl
import io.snabble.pay.app.data.repository.remotedatasource.RemoteDataSource
import io.snabble.pay.app.domain.AccountsRepository

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindAccountsLocalDataSource(
        source: AccountsLocalDataSourceImpl,
    ): AccountsLocalDataSource

    @Binds
    abstract fun bindAccountsRemoteDataSource(
        source: RemoteDataSourceImpl,
    ): RemoteDataSource

    @Binds
    abstract fun bindAccountRepository(
        source: AccountRepositoryImpl
    ): AccountsRepository
}
