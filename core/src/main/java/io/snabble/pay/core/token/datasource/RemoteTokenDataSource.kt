package io.snabble.pay.core.token.datasource

internal interface RemoteTokenDataSource {

    suspend fun getToken(): Token?
}
