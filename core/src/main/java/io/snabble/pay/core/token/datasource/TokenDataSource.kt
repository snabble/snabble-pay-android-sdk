package io.snabble.pay.core.token.datasource

internal interface TokenDataSource {

    suspend fun getToken(): Token?
}
