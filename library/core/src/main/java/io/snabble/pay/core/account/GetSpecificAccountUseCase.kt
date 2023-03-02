package io.snabble.pay.core.account

import io.snabble.pay.core.domain.model.Account

internal fun interface GetSpecificAccountUseCase {

    suspend operator fun invoke(id: String): Result<Account>
}
