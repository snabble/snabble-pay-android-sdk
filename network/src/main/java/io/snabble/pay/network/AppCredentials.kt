package io.snabble.pay.network

data class AppCredentials(
    val id: AppIdentifier,
    val secret: AppSecret,
)

@JvmInline
value class AppIdentifier(val id: String)

@JvmInline
value class AppSecret(val secret: String)
