package io.snabble.pay.app.domain.account.usecase

import io.snabble.pay.app.domain.account.AccountCard

fun interface DeleteAccountUseCase {

    suspend operator fun invoke(accountId: String): AccountCard
}
