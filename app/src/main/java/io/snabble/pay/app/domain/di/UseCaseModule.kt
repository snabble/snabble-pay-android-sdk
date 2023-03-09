package io.snabble.pay.app.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.domain.account.usecase.GetAccountUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountUseCaseImpl
import io.snabble.pay.app.domain.account.usecase.GetAccountsUseCase
import io.snabble.pay.app.domain.account.usecase.GetAccountsUseCaseImpl
import io.snabble.pay.app.domain.account.usecase.UpdateAccountNameUseCase
import io.snabble.pay.app.domain.account.usecase.UpdateAccountNameUseCaseImpl
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.AcceptMandateUseCaseImpl
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCase
import io.snabble.pay.app.domain.mandate.usecase.CreateMandateUseCaseImpl
import io.snabble.pay.app.domain.usecase.GetSessionTokenUseCase
import io.snabble.pay.app.domain.usecase.GetSessionTokenUseCaseImpl

@InstallIn(SingletonComponent::class)
@Module interface UseCaseModule {

    @Binds fun bindGetAccountsUseCase(
        source: GetAccountsUseCaseImpl,
    ): GetAccountsUseCase

    @Binds fun bindGetAccountUseCase(
        source: GetAccountUseCaseImpl,
    ): GetAccountUseCase

    @Binds fun bindUpdateAccountNameUseCase(
        source: UpdateAccountNameUseCaseImpl,
    ): UpdateAccountNameUseCase

    @Binds fun bindGetSessionTokenUseCase(
        source: GetSessionTokenUseCaseImpl,
    ): GetSessionTokenUseCase

    @Binds fun bindCreateMandateUseCase(
        source: CreateMandateUseCaseImpl,
    ): CreateMandateUseCase

    @Binds fun bindAcceptMandateUseCase(
        source: AcceptMandateUseCaseImpl,
    ): AcceptMandateUseCase
}
