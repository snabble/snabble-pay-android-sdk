package io.snabble.pay.app.domain.session

import kotlinx.serialization.Serializable

@Serializable
data class SessionModel(
    val token: String,
    val validUntil: String,
)
