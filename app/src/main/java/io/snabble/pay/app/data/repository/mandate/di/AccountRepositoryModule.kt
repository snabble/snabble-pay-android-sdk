package io.snabble.pay.app.data.repository.mandate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.data.repository.mandate.MandateRepositoryImpl
import io.snabble.pay.app.data.repository.mandate.remotedatasource.MandateRemoteDataSource
import io.snabble.pay.app.data.repository.mandate.remotedatasource.MandateRemoteDataSourceImpl
import io.snabble.pay.app.domain.MandateRepository

@InstallIn(SingletonComponent::class)
@Module interface MandateRepositoryModule {

    @Binds fun bindMandateRemoteDataSource(
        source: MandateRemoteDataSourceImpl,
    ): MandateRemoteDataSource

    @Binds fun bindMandateRepository(
        source: MandateRepositoryImpl,
    ): MandateRepository
}
