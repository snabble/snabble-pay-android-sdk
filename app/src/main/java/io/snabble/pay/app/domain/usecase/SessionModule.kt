package io.snabble.pay.app.domain.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module interface SessionModule {

    @Binds fun bindGetSessionTokenUseCase(
        source: GetSessionTokenUseCaseImpl,
    ): GetSessionTokenUseCase
}
