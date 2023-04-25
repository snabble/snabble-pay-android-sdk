package io.snabble.pay.sdk.internal.token.data.source

import io.snabble.pay.sdk.internal.token.domain.model.Token

internal interface RemoteTokenDataSource {

    suspend fun getToken(): Token?
}
