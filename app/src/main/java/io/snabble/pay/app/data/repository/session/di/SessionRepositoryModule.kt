package io.snabble.pay.app.data.repository.session.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.data.repository.session.SessionRepositoryImpl
import io.snabble.pay.app.domain.session.SessionRepository

@InstallIn(SingletonComponent::class)
@Module interface SessionRepositoryModule {

    @Binds fun bindSessionRepository(
        source: SessionRepositoryImpl,
    ): SessionRepository
}
