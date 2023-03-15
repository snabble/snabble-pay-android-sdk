package io.snabble.pay.app.domain.account.usecase

fun interface SetAccountCardLabelUseCase {

    suspend operator fun invoke(accountId: String, name: String)
}
