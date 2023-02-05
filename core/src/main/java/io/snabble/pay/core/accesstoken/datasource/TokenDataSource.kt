package io.snabble.pay.core.accesstoken.datasource

import java.time.ZonedDateTime

internal interface TokenDataSource {

    suspend fun getToken(): TokenDto?
}

internal suspend fun TokenDataSource.getValidToken(): TokenDto? =
    getToken()
        ?.takeIf { it.expiryDate.isAfter(ZonedDateTime.now()) }
