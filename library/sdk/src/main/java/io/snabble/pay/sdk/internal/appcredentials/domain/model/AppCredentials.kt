package io.snabble.pay.sdk.internal.appcredentials.domain.model

internal data class AppCredentials(
    val id: AppIdentifier,
    val secret: AppSecret,
)

@JvmInline
internal value class AppIdentifier(val value: String)

@JvmInline
internal value class AppSecret(val value: String)
