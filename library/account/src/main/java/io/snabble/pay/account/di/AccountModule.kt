package io.snabble.pay.account.di

import io.snabble.pay.account.data.mapper.AccountCheckMapper
import io.snabble.pay.account.data.repository.AccountsRepositoryImpl
import io.snabble.pay.account.data.service.AccountService
import io.snabble.pay.account.domain.repository.AccountsRepository
import io.snabble.pay.account.domain.usecase.CreateAccountCheckUseCase
import io.snabble.pay.account.domain.usecase.GetAllAccountsUseCase
import io.snabble.pay.account.domain.usecase.GetSpecificAccountUseCase
import io.snabble.pay.account.domain.usecase.RemoveAccountUseCase
import io.snabble.pay.internal.account.data.mapper.AccountMapper
import io.snabble.pay.internal.account.data.mapper.MandateStateMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

/** @suppress Dokka */
val accountModule = module {
    factory { CreateAccountCheckUseCase(get<AccountsRepository>()::getAccountCheck) }
    factory { GetSpecificAccountUseCase(get<AccountsRepository>()::getAccount) }
    factory { GetAllAccountsUseCase(get<AccountsRepository>()::getAccounts) }
    factory { RemoveAccountUseCase(get<AccountsRepository>()::removeAccount) }

    singleOf(::AccountsRepositoryImpl) bind AccountsRepository::class

    single { AccountMapper(mandateStateMapper = get()) }
    single { MandateStateMapper() }
    single { AccountCheckMapper() }

    single { get<Retrofit>().create(AccountService::class.java) } bind AccountService::class
}
