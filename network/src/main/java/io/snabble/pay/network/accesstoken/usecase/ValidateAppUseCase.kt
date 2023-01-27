package io.snabble.pay.network.accesstoken.usecase

import io.snabble.pay.network.accesstoken.AppCredentials

interface ValidateAppUseCase {

    suspend operator fun invoke(): AppCredentials?
}
