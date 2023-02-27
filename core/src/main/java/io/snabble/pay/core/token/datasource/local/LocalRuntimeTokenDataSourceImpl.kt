package io.snabble.pay.core.token.datasource.local

import androidx.annotation.VisibleForTesting
import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.Token

internal class LocalRuntimeTokenDataSourceImpl : LocalTokenDataSource {

    @VisibleForTesting
    internal var token: Token? = null

    override suspend fun getToken(): Token? = token

    override suspend fun saveToken(token: Token) {
        this.token = token
    }
}
