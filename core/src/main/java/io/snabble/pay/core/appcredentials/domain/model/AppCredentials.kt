package io.snabble.pay.core.appcredentials.domain.model

import io.snabble.pay.network.service.register.dto.AppCredentialsDto

data class AppCredentials(
    val id: AppIdentifier,
    val secret: AppSecret,
)

fun AppCredentialsDto.toAppCredentials(): AppCredentials =
    AppCredentials(
        AppIdentifier(id = appId),
        AppSecret(secret = appSecret)
    )

@JvmInline
value class AppIdentifier(val id: String)

@JvmInline
value class AppSecret(val secret: String)
