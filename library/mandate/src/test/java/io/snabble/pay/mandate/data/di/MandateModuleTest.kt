package io.snabble.pay.mandate.data.di

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.mandate.data.mapper.MandateMapper
import io.snabble.pay.mandate.data.mapper.MandateResponseMapper
import io.snabble.pay.mandate.data.mapper.MandateStateMapper
import io.snabble.pay.mandate.data.service.MandateService
import io.snabble.pay.mandate.di.mandateModule
import io.snabble.pay.mandate.domain.repository.MandateRepository
import io.snabble.pay.mandate.domain.usecase.CreateMandateUseCase
import io.snabble.pay.mandate.domain.usecase.GetMandateUseCase
import io.snabble.pay.mandate.domain.usecase.RespondToMandateUseCase
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

class MandateModuleTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(
            listOf(
                mandateModule,
                module {
                    single {
                        mockk<Retrofit> {
                            every { create(MandateService::class.java) } returns mockk()
                        }
                    }
                }
            )
        )
    )

    init {
        "Koin provides" - {

            "the UseCase" - {

                "CreateMandateUseCase" {
                    get<CreateMandateUseCase>().shouldNotBeNull()
                }

                "GetMandateUseCase" {
                    get<GetMandateUseCase>().shouldNotBeNull()
                }

                "RespondToMandateUseCase" {
                    get<RespondToMandateUseCase>().shouldNotBeNull()
                }
            }

            "the MandateRepository" {
                get<MandateRepository>().shouldNotBeNull()
            }

            "the mapper" - {

                "MandateMapper" {
                    get<MandateMapper>().shouldNotBeNull()
                }

                "MandateResponseMapper" {
                    get<MandateResponseMapper>().shouldNotBeNull()
                }

                "MandateStateMapper" {
                    get<MandateStateMapper>().shouldNotBeNull()
                }
            }

            "the MandateService" {
                get<MandateService>().shouldNotBeNull()
            }
        }
    }
}
