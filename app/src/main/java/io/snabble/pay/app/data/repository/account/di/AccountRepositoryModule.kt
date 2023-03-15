package io.snabble.pay.app.data.repository.account.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.data.repository.account.AccountRepositoryImpl
import io.snabble.pay.app.data.repository.account.label.LocalAccountLabelDataSource
import io.snabble.pay.app.data.repository.account.label.LocalAccountLabelDataSourceImpl
import io.snabble.pay.app.data.repository.account.remotedatasource.AccountRemoteDataSource
import io.snabble.pay.app.data.repository.account.remotedatasource.AccountRemoteDataSourceImpl
import io.snabble.pay.app.domain.account.AccountRepository

@InstallIn(SingletonComponent::class)
@Module interface AccountRepositoryModule {

    @Binds fun bindAccountsRemoteDataSource(
        source: AccountRemoteDataSourceImpl,
    ): AccountRemoteDataSource

    @Binds fun bindAccountRepository(
        source: AccountRepositoryImpl,
    ): AccountRepository

    @Binds fun bindLocalAccountLabelDataSource(
        source: LocalAccountLabelDataSourceImpl,
    ): LocalAccountLabelDataSource
}
