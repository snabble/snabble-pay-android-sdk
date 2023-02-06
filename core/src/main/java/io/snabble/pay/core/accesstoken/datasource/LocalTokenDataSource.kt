package io.snabble.pay.core.accesstoken.datasource

internal interface LocalTokenDataSource : TokenDataSource {

    suspend fun saveToken(tokenDto: TokenDto)
}
