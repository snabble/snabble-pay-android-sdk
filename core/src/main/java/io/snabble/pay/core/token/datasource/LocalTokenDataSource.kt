package io.snabble.pay.core.token.datasource

internal interface LocalTokenDataSource {

    suspend fun getToken(): Token?

    suspend fun saveToken(token: Token)
}
