package io.snabble.pay.core.internal.token.data.mapper

import io.snabble.pay.api.service.register.dto.TokenDto
import io.snabble.pay.core.internal.token.domain.model.Token
import io.snabble.pay.core.util.Mapper
import io.snabble.pay.network.okhttp.interceptor.AccessToken

internal class TokenMapper : Mapper<TokenDto, Token> {

    override fun map(from: TokenDto): Token = Token(
        accessToken = AccessToken("${from.tokenType} ${from.token}"),
        expiryDate = from.expiryDate
    )
}
