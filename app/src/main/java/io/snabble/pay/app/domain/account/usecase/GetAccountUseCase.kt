package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.app.domain.account.AccountCardModel
import io.snabble.pay.app.domain.account.AccountRepository
import io.snabble.pay.app.domain.account.toAccountCardModel
import javax.inject.Inject

interface GetAccountUseCase {

    suspend operator fun invoke(accountId: String): AccountCardModel
}

class GetAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
) : GetAccountUseCase {

    override suspend operator fun invoke(accountId: String): AccountCardModel =
        accountRepository.getAccount(accountId).toAccountCardModel()
}
