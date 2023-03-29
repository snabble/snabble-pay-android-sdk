package io.snabble.pay.customerinfo.di

import io.snabble.pay.customerinfo.data.mapper.CustomerInfoMapper
import io.snabble.pay.customerinfo.data.repository.CustomerInfoRepositoryImpl
import io.snabble.pay.customerinfo.data.service.CustomerInfoService
import io.snabble.pay.customerinfo.domain.repository.CustomerInfoRepository
import io.snabble.pay.customerinfo.domain.usecase.CreateCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.GetCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.RemoveCustomerInfoUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

/** @suppress Dokka */
val customerInfoModule = module {
    factory { CreateCustomerInfoUseCase(get<CustomerInfoRepository>()::createCustomerInfo) }
    factory { GetCustomerInfoUseCase(get<CustomerInfoRepository>()::getCustomerInfo) }
    factory { RemoveCustomerInfoUseCase(get<CustomerInfoRepository>()::removeCustomerInfo) }

    singleOf(::CustomerInfoRepositoryImpl) bind CustomerInfoRepository::class

    factoryOf(::CustomerInfoMapper)

    single { get<Retrofit>().create(CustomerInfoService::class.java) }
}
