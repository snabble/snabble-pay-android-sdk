package io.snabble.pay.account.domain.usecase

import io.snabble.pay.account.domain.model.Account
import io.snabble.pay.core.util.Result

fun interface GetAllAccountsUseCase {

    suspend operator fun invoke(): Result<List<Account>>
}
