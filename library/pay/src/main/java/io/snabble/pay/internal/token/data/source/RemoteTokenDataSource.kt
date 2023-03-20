package io.snabble.pay.internal.token.data.source

import io.snabble.pay.internal.token.domain.model.Token

internal interface RemoteTokenDataSource {

    suspend fun getToken(): Token?
}
