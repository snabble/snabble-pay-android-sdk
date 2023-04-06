package io.snabble.pay.app.data.repository.mandate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.data.repository.mandate.MandateRepositoryImpl
import io.snabble.pay.app.domain.mandate.MandateRepository

@InstallIn(SingletonComponent::class)
@Module interface MandateRepositoryModule {

    @Binds fun bindAccountsRemoteDataSource(
        source: MandateRepositoryImpl,
    ): MandateRepository
}
