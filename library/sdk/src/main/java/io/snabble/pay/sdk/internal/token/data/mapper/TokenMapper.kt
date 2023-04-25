package io.snabble.pay.sdk.internal.token.data.mapper

import io.snabble.pay.api.service.register.dto.TokenDto
import io.snabble.pay.api.util.Mapper
import io.snabble.pay.network.okhttp.interceptor.AccessToken
import io.snabble.pay.sdk.internal.token.domain.model.Token

internal class TokenMapper : Mapper<TokenDto, Token> {

    override fun map(from: TokenDto): Token = Token(
        accessToken = AccessToken("${from.tokenType} ${from.token}"),
        expiryDate = from.expiryDate
    )
}
