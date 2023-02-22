package io.snabble.pay.core.token.datasource

internal interface LocalTokenDataSource {

    suspend fun getToken(): TokenDto?

    suspend fun saveToken(tokenDto: TokenDto)
}
