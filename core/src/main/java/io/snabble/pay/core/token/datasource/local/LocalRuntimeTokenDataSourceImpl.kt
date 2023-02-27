package io.snabble.pay.core.token.datasource.local

import io.snabble.pay.core.token.datasource.LocalTokenDataSource
import io.snabble.pay.core.token.datasource.Token

internal class LocalRuntimeTokenDataSourceImpl : LocalTokenDataSource {

    private var token: Token? = null

    override suspend fun getToken(): Token? = token

    override suspend fun saveToken(token: Token) {
        this.token = token
    }
}
