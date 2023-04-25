package io.snabble.pay.sdk.internal.token.data.source

import io.snabble.pay.sdk.internal.token.domain.model.Token

internal interface LocalTokenDataSource {

    suspend fun getToken(): Token?

    suspend fun saveToken(token: Token)
}
