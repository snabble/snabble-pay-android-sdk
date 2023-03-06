package io.snabble.pay.session.data.mapper

import io.snabble.pay.api.util.Mapper
import io.snabble.pay.session.data.dto.TokenDto
import io.snabble.pay.session.domain.model.SessionToken

internal class TokenMapper : Mapper<TokenDto, SessionToken> {

    override fun map(from: TokenDto): SessionToken = with(from) {
        SessionToken(
            createdAt = createdAt,
            id = id,
            refreshAt = refreshAt,
            validUntil = validUntil,
            value = value
        )
    }
}
