package io.snabble.pay.mandate.di

import io.snabble.pay.mandate.data.mapper.MandateMapper
import io.snabble.pay.mandate.data.mapper.MandateResponseMapper
import io.snabble.pay.mandate.data.mapper.MandateStateMapper
import io.snabble.pay.mandate.data.repository.MandateRepositoryImpl
import io.snabble.pay.mandate.data.service.MandateService
import io.snabble.pay.mandate.domain.repository.MandateRepository
import io.snabble.pay.mandate.domain.usecase.CreateMandateUseCase
import io.snabble.pay.mandate.domain.usecase.GetMandateUseCase
import io.snabble.pay.mandate.domain.usecase.RespondToMandateUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val mandateModule = module {
    factory { CreateMandateUseCase(get<MandateRepository>()::createMandate) }
    factory { GetMandateUseCase(get<MandateRepository>()::getMandate) }
    factory { RespondToMandateUseCase(get<MandateRepository>()::respondToMandate) }

    singleOf(::MandateRepositoryImpl) bind MandateRepository::class

    factoryOf(::MandateMapper)
    factoryOf(::MandateResponseMapper)
    factoryOf(::MandateStateMapper)

    single { get<Retrofit>().create(MandateService::class.java) } bind MandateService::class
}
