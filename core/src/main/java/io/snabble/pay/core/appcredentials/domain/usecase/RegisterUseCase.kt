package io.snabble.pay.core.appcredentials.domain.usecase

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.repository.AppCredentialsRepository

interface RegisterUseCase {

    suspend operator fun invoke(): AppCredentials?
}

class RegisterUseCaseImpl(
    private val appCredentialsRepository: AppCredentialsRepository,
) : RegisterUseCase {

    override suspend fun invoke(): AppCredentials? {
        return appCredentialsRepository.getAppCredentials()
    }
}
