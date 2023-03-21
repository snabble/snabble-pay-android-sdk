package io.snabble.pay.internal.token.data.source.local

import androidx.annotation.VisibleForTesting
import io.snabble.pay.internal.token.data.source.LocalTokenDataSource
import io.snabble.pay.internal.token.domain.model.Token

internal class LocalRuntimeTokenDataSourceImpl : LocalTokenDataSource {

    @VisibleForTesting
    internal var token: Token? = null

    override suspend fun getToken(): Token? = token

    override suspend fun saveToken(token: Token) {
        this.token = token
    }
}
