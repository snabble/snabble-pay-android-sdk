package io.snabble.pay.core.usecase

import io.snabble.pay.network.repository.AppCredentials
import io.snabble.pay.network.repository.AppCredentialsRepository

interface ValidateAppUseCase {

    suspend operator fun invoke(): AppCredentials?
}

class ValidateAppUseCaseImpl(
    private val appCredentialsRepository: AppCredentialsRepository,
    private val fetchAppCredentials: FetchAppCredentialsUseCase
) : ValidateAppUseCase {

    override suspend fun invoke(): AppCredentials? {
        var appCredentials = appCredentialsRepository.getAppCredentials()
        if (appCredentials != null) return appCredentials

        appCredentials = fetchAppCredentials()

        return appCredentials
    }
}
