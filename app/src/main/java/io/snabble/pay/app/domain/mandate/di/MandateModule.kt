package io.snabble.pay.app.domain.mandate.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.mandate.MandateRepository
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase

@InstallIn(SingletonComponent::class)
@Module
class MandateModule {

    @Provides
    fun bindCreateMandateUseCase(
        mandateRepository: MandateRepository,
    ) = CreateMandateUseCase(mandateRepository::createMandate)

    @Provides
    fun bindGetMandateUseCase(
        mandateRepository: MandateRepository,
    ) = GetMandateUseCase(mandateRepository::getMandate)

    @Provides
    fun bindAcceptMandateUseCase(
        mandateRepository: MandateRepository,
    ) = AcceptMandateUseCase(mandateRepository::acceptMandate)
}
