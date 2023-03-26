package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.AccountRepository
import io.snabble.pay.app.domain.session.usecase.DeleteSessionUseCase
import io.snabble.pay.app.domain.session.usecase.GetSessionsUseCase
import javax.inject.Inject

interface DeleteAccountUseCase {

    suspend operator fun invoke(accountId: String): AppResult<AccountCard>
}

class DeleteAccountUseCaseImpl @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val deleteSessionUseCase: DeleteSessionUseCase,
    private val accountRepository: AccountRepository,
) : DeleteAccountUseCase {

    override suspend operator fun invoke(accountId: String): AppResult<AccountCard> {
        return when (val result = getSessionsUseCase()) {
            is AppError -> AppError(result.value)
            is AppSuccess -> {
                if (result.value.isEmpty()) {
                    accountRepository.deleteAccount(accountId)
                } else {
                    when (val result = deleteSessionUseCase(result.value.first().id)) {
                        is AppError -> AppError(result.value)
                        is AppSuccess -> {
                            accountRepository.deleteAccount(accountId)
                        }
                    }
                }
            }
        }
    }
}
