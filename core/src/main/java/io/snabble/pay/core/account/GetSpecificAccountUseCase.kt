package io.snabble.pay.core.account

import io.snabble.pay.core.account.repository.AccountsRepository
import io.snabble.pay.core.domain.model.Account

internal fun interface GetSpecificAccountUseCase {

    suspend operator fun invoke(id: String): Result<Account>
}

internal class GetSpecificAccountUseCaseImpl(
    private val repository: AccountsRepository,
) : GetSpecificAccountUseCase {

    override suspend fun invoke(id: String): Result<Account> = repository.getAccount(id)
}
