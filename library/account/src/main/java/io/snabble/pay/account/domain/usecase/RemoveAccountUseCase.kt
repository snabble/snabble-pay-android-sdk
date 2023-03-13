package io.snabble.pay.account.domain.usecase

import io.snabble.pay.account.domain.model.Account

fun interface RemoveAccountUseCase {

    suspend operator fun invoke(id: String): Result<Account>
}
