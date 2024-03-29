package io.snabble.pay.sdk.internal.appcredentials.domain

import io.snabble.pay.sdk.internal.appcredentials.domain.model.AppCredentials

internal fun interface GetAppCredentialsUseCase {

    suspend operator fun invoke(): AppCredentials?
}
