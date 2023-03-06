package io.snabble.pay.session.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AccountIdDto(@SerialName("id") val id: String)
