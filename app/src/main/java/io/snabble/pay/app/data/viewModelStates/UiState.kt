package io.snabble.pay.app.data.viewModelStates

import io.snabble.pay.app.domain.account.AccountCard

sealed interface UiState

object Loading : UiState

object MandateAccepted : UiState

object MandatePendingOrDeclined : UiState
data class ShowAccount(
    val accountCard: AccountCard,
) : UiState

data class ShowAccounts(
    val accountCards: List<AccountCard>,
) : UiState

data class StartValidationFlow(
    val validationLink: String,
) : UiState

data class Error(
    val message: String? = "Ups! Something went wrong",
) : UiState
