package io.snabble.pay.app.domain.account.usecase

import android.util.Log
import io.snabble.pay.account.domain.model.AccountCheck
import io.snabble.pay.app.domain.account.AccountRepository
import javax.inject.Inject

interface AddNewAccountUseCase {

    suspend operator fun invoke(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck>
}

class AddNewAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
) : AddNewAccountUseCase {

    override suspend fun invoke(
        appUri: String,
        city: String,
        twoLetterIsoCountryCode: String,
    ): Result<AccountCheck> =
        accountRepository.addNewAccount(appUri, city, twoLetterIsoCountryCode).also {
            Log.d("xx", "invoke: $it ")
        }
}
