package io.snabble.pay.sdk.internal.appcredentials.data.mapper

import io.snabble.pay.api.service.register.dto.AppCredentialsDto
import io.snabble.pay.api.util.Mapper
import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppCredentials
import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppIdentifier
import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppSecret

internal class AppCredentialsMapper : Mapper<AppCredentialsDto, AppCredentials> {

    override fun map(from: AppCredentialsDto): AppCredentials = AppCredentials(
        id = AppIdentifier(value = from.appId),
        secret = AppSecret(value = from.appSecret)
    )
}
