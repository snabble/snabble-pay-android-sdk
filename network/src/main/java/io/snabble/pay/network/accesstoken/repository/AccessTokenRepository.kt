package io.snabble.pay.network.accesstoken.repository

interface AccessTokenRepository {

    suspend fun getAccessToken(): AccessToken

    suspend fun saveAccessToken(token: AccessToken)
}

@JvmInline
value class AccessToken(val value: String)
