package io.snabble.pay.core.accesstoken.datasource

internal interface TokenDataSource {

    suspend fun getToken(): TokenDto?
}
