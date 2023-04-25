package io.snabble.pay.sdk.internal.token.data

import io.snabble.pay.sdk.internal.token.data.source.LocalTokenDataSource
import io.snabble.pay.sdk.internal.token.data.source.RemoteTokenDataSource
import io.snabble.pay.sdk.internal.token.domain.model.Token
import io.snabble.pay.sdk.internal.token.domain.repository.TokenRepository

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
