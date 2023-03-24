package io.snabble.pay.app.domain.session.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.session.SessionRepository
import io.snabble.pay.app.domain.session.usecase.CreateSessionUseCase
import io.snabble.pay.app.domain.session.usecase.DeleteSessionUseCase
import io.snabble.pay.app.domain.session.usecase.GetSessionUseCase
import io.snabble.pay.app.domain.session.usecase.GetSessionsUseCase
import io.snabble.pay.app.domain.session.usecase.UpdateTokenUseCase

@InstallIn(SingletonComponent::class)
@Module
class MandateModule {

    @Provides
    fun bindCreateSessionUseCase(
        sessionRepository: SessionRepository,
    ) = CreateSessionUseCase(sessionRepository::createSession)

    @Provides
    fun bindDeleteSessionUseCase(
        sessionRepository: SessionRepository,
    ) = DeleteSessionUseCase(sessionRepository::deleteSession)

    @Provides
    fun bindGetSessionUseCase(
        sessionRepository: SessionRepository,
    ) = GetSessionUseCase(sessionRepository::getSession)

    @Provides
    fun bindGetSessionsUseCase(
        sessionRepository: SessionRepository,
    ) = GetSessionsUseCase(sessionRepository::getSessions)

    @Provides
    fun bindUpdateSessionTokenUseCase(
        sessionRepository: SessionRepository,
    ) = UpdateTokenUseCase(sessionRepository::updateToken)
}
