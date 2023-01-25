package io.snabble.pay.network.accesstoken

interface AccessTokenRepository {

    suspend fun getAccessToken(): AccessToken

    suspend fun saveAccessToken(token: AccessToken)
}

@JvmInline
value class AccessToken(val token: String)
