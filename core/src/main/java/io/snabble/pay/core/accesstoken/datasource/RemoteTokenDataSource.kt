package io.snabble.pay.core.accesstoken.datasource

internal interface RemoteTokenDataSource {

    suspend fun getToken(): TokenDto?
}
