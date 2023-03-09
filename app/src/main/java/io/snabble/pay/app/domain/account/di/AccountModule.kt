package io.snabble.pay.app.domain.account.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.account.usecase.AccountManager
import io.snabble.pay.app.domain.account.usecase.AccountManagerImpl
import io.snabble.pay.app.domain.account.usecase.GetAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountUseCaseImpl
import io.snabble.pay.app.domain.account.usecase.GetAccountsUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountsUseCaseImpl
import io.snabble.pay.app.domain.account.usecase.UpdateAccountNameUseCase
import io.snabble.pay.app.domain.account.usecase.UpdateAccountNameUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module interface AccountModule {

    @Binds fun bindGetAccountsUseCase(
        source: GetAccountsUseCaseImpl,
    ): GetAccountsUseCase

    @Binds fun bindGetAccountUseCase(
        source: GetAccountUseCaseImpl,
    ): GetAccountUseCase

    @Binds fun bindUpdateAccountNameUseCase(
        source: UpdateAccountNameUseCaseImpl,
    ): UpdateAccountNameUseCase

    @Binds fun bindAccountManager(
        source: AccountManagerImpl,
    ): AccountManager
}

