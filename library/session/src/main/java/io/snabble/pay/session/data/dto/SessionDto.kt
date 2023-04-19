package io.snabble.pay.session.data.dto

import io.snabble.pay.api.util.ZonedDateTimeAsText
import io.snabble.pay.internal.account.data.dto.AccountDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SessionDto(
    @SerialName("createdAt") val createdAt: ZonedDateTimeAsText,
    @SerialName("expiresAt") val expiresAt: ZonedDateTimeAsText,
    @SerialName("id") val id: String,
    @SerialName("account") val accountDto: AccountDto,
    @SerialName("token") val token: TokenDto,
    @SerialName("transaction") val transaction: TransactionDto? = null,
)
