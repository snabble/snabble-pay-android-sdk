package io.snabble.pay.app.domain.account.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.account.AccountRepository
import io.snabble.pay.app.domain.account.usecase.AddAccountUseCase
import io.snabble.pay.app.domain.account.usecase.DeleteAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountCardUseCase
import io.snabble.pay.app.domain.account.usecase.GetAllAccountCardsUseCase
import io.snabble.pay.app.domain.account.usecase.SetAccountCardLabelUseCase

@InstallIn(SingletonComponent::class)
@Module
class AccountModule {

    @Provides
    fun provideAddNewAccountUseCase(
        accountRepository: AccountRepository,
    ) = AddAccountUseCase(accountRepository::addNewAccount)

    @Provides
    fun provideAllAccountUseCase(
        accountRepository: AccountRepository,
    ) = GetAccountCardUseCase(accountRepository::getAccount)

    @Provides
    fun provideGetAllAccountsUseCase(
        accountRepository: AccountRepository,
    ) = GetAllAccountCardsUseCase(accountRepository::getAccounts)

    @Provides
    fun provideSetAccountLabelUseCase(
        accountRepository: AccountRepository,
    ) = SetAccountCardLabelUseCase(accountRepository::setAccountLabel)

    @Provides
    fun provideDeleteAccountUseCase(
        accountRepository: AccountRepository,
    ) = DeleteAccountUseCase(accountRepository::deleteAccount)
}
