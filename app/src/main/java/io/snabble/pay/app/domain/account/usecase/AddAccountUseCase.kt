package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.app.data.utils.AppResult

fun interface AddAccountUseCase {

    suspend operator fun invoke(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): AppResult<AccountCheck>
}
