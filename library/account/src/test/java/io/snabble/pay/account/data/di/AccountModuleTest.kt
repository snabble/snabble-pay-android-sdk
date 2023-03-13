package io.snabble.pay.account.data.di

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.FreeSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import io.snabble.pay.account.data.mapper.AccountCheckMapper
import io.snabble.pay.account.data.mapper.AccountMapper
import io.snabble.pay.account.data.mapper.MandateStateMapper
import io.snabble.pay.account.data.service.AccountService
import io.snabble.pay.account.di.accountModule
import io.snabble.pay.account.domain.repository.AccountsRepository
import io.snabble.pay.account.domain.usecase.CreateAccountCheckUseCase
import io.snabble.pay.account.domain.usecase.GetAllAccountsUseCase
import io.snabble.pay.account.domain.usecase.GetSpecificAccountUseCase
import io.snabble.pay.account.domain.usecase.RemoveAccountUseCase
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

class AccountModuleTest : FreeSpec(), KoinTest {

    override fun extensions(): List<Extension> = listOf(
        KoinExtension(
            listOf(
                accountModule,
                module {
                    single {
                        mockk<Retrofit> {
                            every { create(AccountService::class.java) } returns mockk()
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
                    get<CreateAccountCheckUseCase>().shouldNotBeNull()
                }

                "GetSpecificAccountUseCase" {
                    get<GetSpecificAccountUseCase>().shouldNotBeNull()
                }

                "GetAllAccountsUseCase" {
                    get<GetAllAccountsUseCase>().shouldNotBeNull()
                }

                "RemoveAccountUseCase" {
                    get<RemoveAccountUseCase>().shouldNotBeNull()
                }
            }

            "the AccountsRepository" {
                get<AccountsRepository>().shouldNotBeNull()
            }

            "the mapper" - {

                "AccountMapper" {
                    get<AccountMapper>().shouldNotBeNull()
                }

                "MandateStateMapper" {
                    get<MandateStateMapper>().shouldNotBeNull()
                }

                "AccountCheckMapper" {
                    get<AccountCheckMapper>().shouldNotBeNull()
                }
            }

            "the MandateService" {
                get<AccountService>().shouldNotBeNull()
            }
        }
    }
}
