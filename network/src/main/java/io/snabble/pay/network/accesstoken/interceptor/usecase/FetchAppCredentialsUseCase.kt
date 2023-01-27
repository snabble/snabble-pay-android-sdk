package io.snabble.pay.network.accesstoken.interceptor.usecase

import io.snabble.pay.network.api.data.AppCredentials
import io.snabble.pay.network.api.interfaces.GetAppCredentials
import retrofit2.Retrofit
import retrofit2.create

interface FetchAppCredentialsUseCase {

    suspend operator fun invoke(): AppCredentials?
}

class FetchAppCredentialsUseCaseImpl : FetchAppCredentialsUseCase {

    override suspend fun invoke(): AppCredentials? {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.snabble.io/app/")
            .build()
        val registerEndpoint = retrofit.create(GetAppCredentials::class.java)
        val response =  registerEndpoint.getAppCredentials().execute()
        return response.body()?.credentials
    }
}
