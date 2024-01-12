package io.snabble.pay.app.domain.transactions

import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.domain.session.SessionModel
import io.snabble.pay.app.domain.session.usecase.GetSessionsUseCase
import io.snabble.pay.session.domain.model.TransactionState
import javax.inject.Inject

interface GetAllPurchasesUseCase {

    suspend operator fun invoke(): List<PurchasesModel>?
}

class GetAllPurchasesUseCaseImpl @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
) : GetAllPurchasesUseCase {

    override suspend operator fun invoke(): List<PurchasesModel>? {
        return when (val sessions = getSessionsUseCase()) {
            is AppSuccess -> {
                sessions.value.mapToTransactionsModel()
            }

            is AppError -> null
        }
    }

    private fun List<SessionModel>.mapToTransactionsModel() =
        filter { validStates.contains(it.transaction?.state) }.mapNotNull { session ->
            session.transaction?.let {
                PurchasesModel(
                    data = it.finalizedAt,
                    amount = it.amount.toString(),
                    cardName = session.accountCard.name,
                    state = if (it.state == TransactionState.SUCCESSFUL) PurchasesState.SUCCESS else PurchasesState.FAILED
                )
            }
        }

    private val validStates = listOf(
        TransactionState.SUCCESSFUL,
        TransactionState.ABORTED,
        TransactionState.ERRORED,
        TransactionState.FAILED
    )
}
