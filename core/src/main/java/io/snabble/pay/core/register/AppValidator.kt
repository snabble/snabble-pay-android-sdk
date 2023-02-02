package io.snabble.pay.core.register

import io.snabble.pay.core.appcredentials.domain.model.AppCredentials
import io.snabble.pay.core.register.usecase.ValidateAppUseCase

interface AppValidator {

    suspend fun registerApp(): AppCredentials?
}

class AppValidatorImpl(
    private val validateApp: ValidateAppUseCase,
) : AppValidator {

    override suspend fun registerApp(): AppCredentials? {
        return validateApp()
    }
}
