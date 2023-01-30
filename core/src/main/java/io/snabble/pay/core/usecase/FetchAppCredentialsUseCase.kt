package io.snabble.pay.core.usecase

import io.snabble.pay.network.api.interfaces.GetAppCredentials
import io.snabble.pay.network.repository.AppCredentials
import retrofit2.Retrofit

interface FetchAppCredentialsUseCase {

    suspend operator fun invoke(): AppCredentials?
}

class FetchAppCredentialsUseCaseImpl : FetchAppCredentialsUseCase {

    override suspend fun invoke(): AppCredentials? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.snabble.io/app/")
            .build()
        val registerEndpoint = retrofit.create(GetAppCredentials::class.java)
        val response = registerEndpoint.getAppCredentials().execute()
        //return response.body()?.credentials
        TODO()
    }
}
