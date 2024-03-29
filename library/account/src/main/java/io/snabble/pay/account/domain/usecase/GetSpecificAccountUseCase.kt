package io.snabble.pay.account.domain.usecase

import io.snabble.pay.core.util.Result
import io.snabble.pay.shared.account.domain.model.Account

/** @suppress Dokka */
fun interface GetSpecificAccountUseCase {

    suspend operator fun invoke(id: String): Result<Account>
}
