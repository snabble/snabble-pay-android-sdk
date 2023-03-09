package io.snabble.pay.app.domain.mandate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCaseImpl
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCaseImpl
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCaseImpl
import io.snabble.pay.app.domain.mandate.usecase.MandateManager
import io.snabble.pay.app.domain.mandate.usecase.MandateManagerImpl

@InstallIn(SingletonComponent::class)
@Module interface MandateModule {

    @Binds fun bindCreateMandateUseCase(
        source: CreateMandateUseCaseImpl,
    ): CreateMandateUseCase

    @Binds fun bindGetMandateUseCase(
        source: GetMandateUseCaseImpl,
    ): GetMandateUseCase

    @Binds fun bindAcceptMandateUseCase(
        source: AcceptMandateUseCaseImpl,
    ): AcceptMandateUseCase

    @Binds fun bindMandateManager(
        source: MandateManagerImpl,
    ): MandateManager
}
