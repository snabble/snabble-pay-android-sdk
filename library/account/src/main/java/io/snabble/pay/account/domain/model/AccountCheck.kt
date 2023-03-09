package io.snabble.pay.account.domain.model

data class AccountCheck(
    val validationLink: String,
    val appUri: String,
)
