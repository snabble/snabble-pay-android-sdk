package io.snabble.pay.network.repository

interface AppCredentialsRepository {

    suspend fun getAppCredentials(): AppCredentials?
}

data class AppCredentials(
    val id: AppIdentifier,
    val secret: AppSecret,
    val urlScheme: AppUrlScheme,
)

@JvmInline
value class AppIdentifier(val id: String)

@JvmInline
value class AppSecret(val secret: String)

@JvmInline
value class AppUrlScheme(val scheme: String)
