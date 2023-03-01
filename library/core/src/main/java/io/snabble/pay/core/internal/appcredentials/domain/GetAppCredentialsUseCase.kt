package io.snabble.pay.core.internal.appcredentials.domain

import io.snabble.pay.core.internal.appcredentials.domain.model.AppCredentials

internal fun interface GetAppCredentialsUseCase {

    suspend operator fun invoke(): AppCredentials?
}
