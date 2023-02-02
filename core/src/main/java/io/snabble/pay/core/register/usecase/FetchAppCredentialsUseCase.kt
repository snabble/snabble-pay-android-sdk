package io.snabble.pay.core.register.usecase

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.core.appcredentials.domain.model.AppSecret
import io.snabble.pay.network.service.register.AppRegistrationService
import retrofit2.Retrofit

interface FetchAppCredentialsUseCase {

    suspend operator fun invoke(): AppCredentials?
}

class FetchAppCredentialsUseCaseImpl : FetchAppCredentialsUseCase {

    override suspend fun invoke(): AppCredentials? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.snabble.io/app/")
            .build()
        val registerEndpoint = retrofit.create(AppRegistrationService::class.java)
        val response = registerEndpoint.getAppCredentials().execute()
        val apiCredentials = response.body() ?: return null
        val (id, secret) = apiCredentials
        return AppCredentials(
            id = AppIdentifier(id),
            secret = AppSecret(secret),
        )
    }
}