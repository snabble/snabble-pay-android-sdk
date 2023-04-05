package io.snabble.pay.account.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.shared.account.domain.model.Account

fun interface RemoveAccountUseCase {

    suspend operator fun invoke(id: String): Result<Account>
}
