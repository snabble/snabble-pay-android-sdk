package io.snabble.pay.core.token

import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.RemoteTokenDataSource
import io.snabble.pay.core.token.datasource.Token

internal interface TokenRepository {

    suspend fun getToken(): Token?

    suspend fun getNewToken(): Token?
}

internal class TokenRepositoryImpl(
    private val localTokenDataSource: LocalTokenDataSource,
    private val remoteTokenDataSource: RemoteTokenDataSource,
) : TokenRepository {

    override suspend fun getToken(): Token? =
        localTokenDataSource.getToken()
            ?: getAndSaveNewToken()

    override suspend fun getNewToken(): Token? =
        getAndSaveNewToken()

    private suspend fun getAndSaveNewToken(): Token? =
        remoteTokenDataSource.getToken()
            ?.also { localTokenDataSource.saveToken(it) }
}
