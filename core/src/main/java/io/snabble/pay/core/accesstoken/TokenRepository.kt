package io.snabble.pay.core.accesstoken

import io.snabble.pay.core.accesstoken.datasource.LocalTokenDataSource
import io.snabble.pay.core.accesstoken.datasource.RemoteTokenDataSource
import io.snabble.pay.core.accesstoken.datasource.TokenDto

internal interface TokenRepository {

    suspend fun getToken(): TokenDto?
}

internal class TokenRepositoryImpl(
    private val localTokenDataSource: LocalTokenDataSource,
    private val remoteTokenDataSource: RemoteTokenDataSource,
) : TokenRepository {

    override suspend fun getToken(): TokenDto? {
        return localTokenDataSource.getToken()
            ?: remoteTokenDataSource.getToken()
                ?.also { localTokenDataSource.saveToken(it) }
    }
}
