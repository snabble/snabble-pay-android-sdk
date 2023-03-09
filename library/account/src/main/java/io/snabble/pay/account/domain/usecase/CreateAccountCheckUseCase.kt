package io.snabble.pay.account.domain.usecase

import io.snabble.pay.account.domain.model.AccountCheck

fun interface CreateAccountCheckUseCase {

    suspend operator fun invoke(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>
}
