package io.snabble.pay.sdk.internal.token.domain.repository

import io.snabble.pay.sdk.internal.token.domain.model.Token

internal interface TokenRepository {

    suspend fun getToken(): Token?

    suspend fun getNewToken(): Token?
}
