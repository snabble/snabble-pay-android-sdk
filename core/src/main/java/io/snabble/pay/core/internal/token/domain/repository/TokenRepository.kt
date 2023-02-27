package io.snabble.pay.core.internal.token.domain.repository

import io.snabble.pay.core.internal.token.domain.model.Token

internal interface TokenRepository {

    suspend fun getToken(): Token?

    suspend fun getNewToken(): Token?
}
