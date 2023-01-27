package io.snabble.pay.network.accesstoken.interceptor.usecase

import io.snabble.pay.network.accesstoken.repository.AppCredentialsRepository
import io.snabble.pay.network.api.data.AppCredentials

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
