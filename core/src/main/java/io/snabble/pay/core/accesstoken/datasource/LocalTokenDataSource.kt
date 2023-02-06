package io.snabble.pay.core.accesstoken.datasource

internal interface LocalTokenDataSource {

    suspend fun getToken(): TokenDto?

    suspend fun saveToken(tokenDto: TokenDto)
}
