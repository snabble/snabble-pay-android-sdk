package io.snabble.pay.core.account

import io.snabble.pay.core.account.repository.AccountsRepository
import io.snabble.pay.core.domain.model.Account

internal fun interface GetAllAccountsUseCase {

    suspend operator fun invoke(): Result<List<Account>>
}

internal class GetAllAccountsUseCaseImpl(
    private val repository: AccountsRepository,
) : GetAllAccountsUseCase {

    override suspend fun invoke(): Result<List<Account>> =
        repository.getAccounts()
}
