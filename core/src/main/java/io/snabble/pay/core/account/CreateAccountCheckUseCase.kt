package io.snabble.pay.core.account

import io.snabble.pay.core.account.repository.AccountsRepository
import io.snabble.pay.core.domain.model.AccountCheck

internal fun interface CreateAccountCheckUseCase {

    suspend operator fun invoke(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>
}

internal class CreateAccountCheckUseCaseImpl(
    private val repository: AccountsRepository,
) : CreateAccountCheckUseCase {

    override suspend fun invoke(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck> =
        repository.getAccountCheck(appUri, city, twoLetterIsoCountryCode)
}
