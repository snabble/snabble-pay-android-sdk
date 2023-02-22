package io.snabble.pay.core.token

import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.RemoteTokenDataSource
import io.snabble.pay.core.token.datasource.TokenDto

internal interface TokenRepository {

    suspend fun getToken(): TokenDto?

    suspend fun getNewToken(): TokenDto?
}

internal class TokenRepositoryImpl(
    private val localTokenDataSource: LocalTokenDataSource,
    private val remoteTokenDataSource: RemoteTokenDataSource,
) : TokenRepository {

    override suspend fun getToken(): TokenDto? =
        localTokenDataSource.getToken()
            ?: getAndSaveNewToken()

    override suspend fun getNewToken(): TokenDto? =
        getAndSaveNewToken()

    private suspend fun getAndSaveNewToken(): TokenDto? =
        remoteTokenDataSource.getToken()
            ?.also { localTokenDataSource.saveToken(it) }
}
