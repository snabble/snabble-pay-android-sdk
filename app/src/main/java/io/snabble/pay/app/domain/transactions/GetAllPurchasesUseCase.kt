package io.snabble.pay.app.domain.transactions

import io.snabble.pay.app.data.utils.AppError
import io.snabble.pay.app.data.utils.AppSuccess
import io.snabble.pay.app.domain.session.SessionModel
import io.snabble.pay.app.domain.session.usecase.GetSessionsUseCase
import io.snabble.pay.session.domain.model.TransactionState
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

interface GetAllPurchasesUseCase {

    suspend operator fun invoke(): Flow<List<PurchasesModel>>
}

class GetAllPurchasesUseCaseImpl @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
) : GetAllPurchasesUseCase {

    override suspend operator fun invoke(): Flow<List<PurchasesModel>> = flow {
        while (currentCoroutineContext().isActive) {
            when (val sessions = getSessionsUseCase()) {
                is AppSuccess -> {
                    emit(sessions.value.mapToTransactionsModel())
                }

                is AppError -> {
                    // Don't emit
                }
            }
            delay(20.seconds)
        }
    }

    private fun List<SessionModel>.mapToTransactionsModel() =
        filter { validStates.contains(it.transaction?.state) }.mapNotNull { session ->
            session.transaction?.let {
                PurchasesModel(
                    data = it.finalizedAt,
                    amount = it.amount,
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
