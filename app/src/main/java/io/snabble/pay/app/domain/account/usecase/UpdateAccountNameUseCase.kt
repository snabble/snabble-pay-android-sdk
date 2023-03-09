package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.app.domain.account.AccountRepository
import javax.inject.Inject

interface UpdateAccountNameUseCase {

    suspend operator fun invoke(accountId: String, name: String)
}

class UpdateAccountNameUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
) : UpdateAccountNameUseCase {

    override suspend operator fun invoke(accountId: String, name: String) =
        accountRepository.updateAccountName(accountId, name)
}
