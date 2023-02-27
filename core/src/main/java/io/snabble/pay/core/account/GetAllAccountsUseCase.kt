package io.snabble.pay.core.account

import io.snabble.pay.core.domain.model.Account

internal fun interface GetAllAccountsUseCase {

    suspend operator fun invoke(): Result<List<Account>>
}
