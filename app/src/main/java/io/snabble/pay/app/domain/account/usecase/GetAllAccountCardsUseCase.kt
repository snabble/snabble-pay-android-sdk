package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.app.data.utils.AppResult
import io.snabble.pay.app.domain.account.AccountCard
import kotlinx.coroutines.flow.Flow

fun interface GetAllAccountCardsUseCase {

    suspend operator fun invoke(): Flow<AppResult<List<AccountCard>>>
}
