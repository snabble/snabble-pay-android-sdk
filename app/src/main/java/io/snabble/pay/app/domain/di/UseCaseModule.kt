package io.snabble.pay.app.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.usecase.GetAccountsUseCase
import io.snabble.pay.app.domain.usecase.GetAccountsUseCaseImpl
import io.snabble.pay.app.domain.usecase.GetSessionTokenUseCase
import io.snabble.pay.app.domain.usecase.GetSessionTokenUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module interface UseCaseModule {

    @Binds fun bindGetAccountsUseCase(
        source: GetAccountsUseCaseImpl,
    ): GetAccountsUseCase

    @Binds fun bingGetSessionTokenUseCase(
        source: GetSessionTokenUseCaseImpl,
    ): GetSessionTokenUseCase
}
