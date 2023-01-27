package io.snabble.pay.network.accesstoken.interceptor.usecase

import io.snabble.pay.network.api.data.AppCredentials

interface FetchAppCredentialsUseCase {

    suspend operator fun invoke(): AppCredentials?
}

class FetchAppCredentialsUseCaseImpl : FetchAppCredentialsUseCase {

    override suspend fun invoke(): AppCredentials? {
        TODO("Not yet implemented")
    }
}
