package io.snabble.pay.core.domain.model

data class AccountCheck(
    val validationLink: String,
    val appUri: String,
)
