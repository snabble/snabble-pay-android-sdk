package io.snabble.pay.core.appcredentials.domain.model

import io.snabble.pay.api.service.register.dto.AppCredentialsDto

data class AppCredentials(
    val id: AppIdentifier,
    val secret: AppSecret,
)

@JvmInline
value class AppIdentifier(val value: String)

@JvmInline
value class AppSecret(val value: String)

fun AppCredentials.toAppCredentialsDto(): AppCredentialsDto =
    AppCredentialsDto(
        appId = id.value,
        appSecret = secret.value
    )

fun AppCredentialsDto.toAppCredentials(): AppCredentials =
    AppCredentials(
        AppIdentifier(value = appId),
        AppSecret(value = appSecret)
    )
