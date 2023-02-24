package io.snabble.pay.core.account.di

import io.snabble.pay.core.account.CreateAccountCheckUseCase
import io.snabble.pay.core.account.GetAllAccountsUseCase
import io.snabble.pay.core.account.GetSpecificAccountUseCase
import io.snabble.pay.core.account.repository.AccountsRepository
import io.snabble.pay.core.account.repository.AccountsRepositoryImpl
import io.snabble.pay.core.domain.mapper.AccountCheckMapper
import io.snabble.pay.core.domain.mapper.AccountMapper
import org.koin.dsl.bind
import org.koin.dsl.module

val accountModule = module {
    single { CreateAccountCheckUseCase(get<AccountsRepository>()::getAccountCheck) }
    single { GetSpecificAccountUseCase(get<AccountsRepository>()::getAccount) }
    single { GetAllAccountsUseCase(get<AccountsRepository>()::getAccounts) }

    single {
        AccountsRepositoryImpl(
            accountService = get(),
            accountMapper = get<AccountMapper>(),
            accountCheckMapper = get<AccountCheckMapper>()
        )
    } bind AccountsRepository::class
}
