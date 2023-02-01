package io.snabble.pay.network

interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?
}
