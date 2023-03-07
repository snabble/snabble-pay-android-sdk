package io.snabble.pay.app.domain.home.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.home.usecase.GetAccountsUseCase
import io.snabble.pay.app.domain.home.usecase.GetAccountsUseCaseImpl
import io.snabble.pay.app.domain.home.usecase.GetSessionTokenUseCase
import io.snabble.pay.app.domain.home.usecase.GetSessionTokenUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetAccountsUseCase(
        source: GetAccountsUseCaseImpl,
    ): GetAccountsUseCase

    @Binds
    abstract fun bingGetSessionTokenUseCase(
        source: GetSessionTokenUseCaseImpl,
    ): GetSessionTokenUseCase
}
