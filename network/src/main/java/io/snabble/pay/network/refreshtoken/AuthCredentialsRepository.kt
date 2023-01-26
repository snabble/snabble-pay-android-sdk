package io.snabble.pay.network.refreshtoken

interface AuthCredentialsRepository {

    suspend fun getAuthCredentials(): AuthCredentials
}

data class AuthCredentials(
    val id: AppIdentifier,
    val secret: AppSecret
)

@JvmInline
value class AppIdentifier(val id: String)

@JvmInline
value class AppSecret(val secret: String)
