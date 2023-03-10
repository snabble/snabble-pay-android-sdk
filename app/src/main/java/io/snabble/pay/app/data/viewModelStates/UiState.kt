package io.snabble.pay.app.data.viewModelStates

import io.snabble.pay.app.domain.account.AccountCardModel

sealed interface UiState

object Loading : UiState

data class ShowAccount(
    val accountCardModel: AccountCardModel,
) : UiState

data class ShowAccounts(
    val accounts: List<AccountCardModel>,
) : UiState

data class StartValidationFlow(
    val validationLink: String,
) : UiState

data class Error(
    val message: String,
) : UiState
