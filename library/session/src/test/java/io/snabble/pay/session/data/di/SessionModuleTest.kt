package io.snabble.pay.session.data.di

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.internal.di.sharedModule
import io.snabble.pay.session.data.mapper.SessionMapper
import io.snabble.pay.session.data.mapper.TokenMapper
import io.snabble.pay.session.data.mapper.TransactionMapper
import io.snabble.pay.session.data.mapper.TransactionStateMapper
import io.snabble.pay.session.data.repository.SessionRepositoryImpl
import io.snabble.pay.session.data.service.SessionService
import io.snabble.pay.session.di.sessionModule
import io.snabble.pay.session.domain.usecase.CreateSessionUseCase
import io.snabble.pay.session.domain.usecase.DeleteSessionUseCase
import io.snabble.pay.session.domain.usecase.GetAllSessionsUseCase
import io.snabble.pay.session.domain.usecase.GetSessionUseCase
import io.snabble.pay.session.domain.usecase.UpdateSessionTokenUseCase
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

class SessionModuleTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(
            listOf(
                sessionModule,
                sharedModule,
                module {
                    single {
                        mockk<Retrofit> {
                            every { create(SessionService::class.java) } returns mockk()
                        }
                    }
                }
            )
        )
    )

    init {
        "Koin provides" - {

            "the UseCase" - {

                "CreateSessionUseCase" {
                    get<CreateSessionUseCase>().shouldNotBeNull()
                }

                "DeleteSessionUseCase" {
                    get<DeleteSessionUseCase>().shouldNotBeNull()
                }

                "GetAllSessionsUseCase" {
                    get<GetAllSessionsUseCase>().shouldNotBeNull()
                }

                "GetSessionUseCase" {
                    get<GetSessionUseCase>().shouldNotBeNull()
                }

                "UpdateSessionTokenUseCase" {
                    get<UpdateSessionTokenUseCase>().shouldNotBeNull()
                }
            }

            "the SessionRepositoryImpl" {
                get<SessionRepositoryImpl>().shouldNotBeNull()
            }

            "the mapper" - {

                "SessionMapper" {
                    get<SessionMapper>().shouldNotBeNull()
                }

                "TokenMapper" {
                    get<TokenMapper>().shouldNotBeNull()
                }

                "TransactionMapper" {
                    get<TransactionMapper>().shouldNotBeNull()
                }

                "TransactionStateMapper" {
                    get<TransactionStateMapper>().shouldNotBeNull()
                }
            }

            "the SessionService" {
                get<SessionService>().shouldNotBeNull()
            }
        }
    }
}
