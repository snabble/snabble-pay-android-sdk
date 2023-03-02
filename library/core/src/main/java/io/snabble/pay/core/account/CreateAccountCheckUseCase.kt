package io.snabble.pay.core.account

import io.snabble.pay.core.domain.model.AccountCheck

internal fun interface CreateAccountCheckUseCase {

    suspend operator fun invoke(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>
}
