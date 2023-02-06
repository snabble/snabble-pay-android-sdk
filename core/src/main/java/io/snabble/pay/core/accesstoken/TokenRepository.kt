package io.snabble.pay.core.accesstoken

import io.snabble.pay.core.accesstoken.datasource.LocalTokenDataSource
import io.snabble.pay.core.accesstoken.datasource.RemoteTokenDataSource
import io.snabble.pay.core.accesstoken.datasource.TokenDto
import io.snabble.pay.core.accesstoken.datasource.getValidToken

internal interface TokenRepository {

    suspend fun getToken(): TokenDto?
}

internal class TokenRepositoryImpl(
    private val localTokenDataSource: LocalTokenDataSource,
    private val remoteTokenDataSource: RemoteTokenDataSource,
) : TokenRepository {

    override suspend fun getToken(): TokenDto? {
        return localTokenDataSource.getValidToken()
            ?: remoteTokenDataSource.getValidToken()
                ?.also { localTokenDataSource.saveToken(it) }
    }
}
