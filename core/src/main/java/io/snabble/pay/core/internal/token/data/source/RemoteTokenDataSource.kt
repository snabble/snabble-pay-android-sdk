package io.snabble.pay.core.internal.token.data.source

import io.snabble.pay.core.internal.token.domain.model.Token

internal interface RemoteTokenDataSource {

    suspend fun getToken(): Token?
}
