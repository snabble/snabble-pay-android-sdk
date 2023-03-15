package io.snabble.pay.app.domain.mandate.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.GetMandateUseCase
import io.snabble.pay.core.SnabblePay

@InstallIn(SingletonComponent::class)
@Module
class MandateModule {

    @Provides
    fun bindCreateMandateUseCase(
        snabblePay: SnabblePay,
    ) = CreateMandateUseCase(snabblePay::createMandate)

    @Provides
    fun bindGetMandateUseCase(
        snabblePay: SnabblePay,
    ) = GetMandateUseCase(snabblePay::getMandate)

    @Provides
    fun bindAcceptMandateUseCase(
        snabblePay: SnabblePay,
    ) = AcceptMandateUseCase(snabblePay::acceptMandate)
}
