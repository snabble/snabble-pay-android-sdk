package io.snabble.pay.network.accesstoken.interceptor.usecase

import io.snabble.pay.network.accesstoken.repository.AppCredentials

interface ValidateAppUseCase {

    suspend operator fun invoke(): AppCredentials?
}
