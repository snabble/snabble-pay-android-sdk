package io.snabble.pay.internal.appcredentials.domain

import io.snabble.pay.internal.appcredentials.domain.model.AppCredentials

internal fun interface GetAppCredentialsUseCase {

    suspend operator fun invoke(): AppCredentials?
}
