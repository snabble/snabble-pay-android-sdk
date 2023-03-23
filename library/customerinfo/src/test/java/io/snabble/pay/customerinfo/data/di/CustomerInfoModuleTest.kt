package io.snabble.pay.customerinfo.data.di

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.customerinfo.data.mapper.CustomerInfoMapper
import io.snabble.pay.customerinfo.data.service.CustomerInfoService
import io.snabble.pay.customerinfo.di.customerInfoModule
import io.snabble.pay.customerinfo.domain.repository.CustomerInfoRepository
import io.snabble.pay.customerinfo.domain.usecase.CreateCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.GetCustomerInfoUseCase
import io.snabble.pay.customerinfo.domain.usecase.RemoveCustomerInfoUseCase
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

class CustomerInfoModuleTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(
            listOf(
                customerInfoModule,
                module {
                    single {
                        mockk<Retrofit> {
                            every { create(CustomerInfoService::class.java) } returns mockk()
                        }
                    }
                }
            )
        )
    )

    init {
        "Koin provides" - {

            "the UseCase" - {

                "CreateAccountCheckUseCase" {
                    get<CreateCustomerInfoUseCase>().shouldNotBeNull()
                }

                "GetSpecificAccountUseCase" {
                    get<GetCustomerInfoUseCase>().shouldNotBeNull()
                }

                "GetAllAccountsUseCase" {
                    get<RemoveCustomerInfoUseCase>().shouldNotBeNull()
                }
            }

            "the AccountsRepository" {
                get<CustomerInfoRepository>().shouldNotBeNull()
            }

            "the mapper AccountMapper" {
                get<CustomerInfoMapper>().shouldNotBeNull()
            }

            "the MandateService" {
                get<CustomerInfoService>().shouldNotBeNull()
            }
        }
    }
}
