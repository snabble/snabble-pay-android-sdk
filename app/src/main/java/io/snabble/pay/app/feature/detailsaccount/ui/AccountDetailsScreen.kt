package io.snabble.pay.app.feature.detailsaccount.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import io.snabble.pay.app.feature.detailsaccount.AccountDeleted
import io.snabble.pay.app.feature.detailsaccount.DetailsAccountViewModel
import io.snabble.pay.app.feature.detailsaccount.Loading
import io.snabble.pay.app.feature.detailsaccount.ShowAccount
import io.snabble.pay.app.ui.widgets.AlertWidget
import io.snabble.pay.core.PayError

@Destination(
    navArgsDelegate = AccountDetailsScreenNavArgs::class,
    deepLinks = [
        DeepLink(uriPattern = "snabble-pay://account/check?accountId={accountId}")
    ]
)
@Composable
fun AccountDetailsScreen(
    viewModel: DetailsAccountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator?,
    resultBackNavigator: ResultBackNavigator<Boolean>?,
) {
    val openDialog = remember {
        mutableStateOf(false)
    }
    val error = viewModel.error.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.error.collect {
            openDialog.value = true
        }
    }

    if (openDialog.value) {
        when (val err = error.value) {
            is PayError -> AlertWidget(
                reason = err.reason.name,
                message = err.message.toString(),
                onDismiss = { openDialog.value = false })
        }
    }

    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is Loading -> LoadingScreen(navigator = navigator)

        is ShowAccount -> {
            AccountDetails(
                navigator = navigator,
                mandate = state.mandate,
                accountCard = state.accountCard,
                onLabelChange = viewModel::updateAccountName,
                onMandateAccept = viewModel::acceptMandate,
                onDeleteAccount = viewModel::deleteAccount
            )
        }

        is AccountDeleted -> resultBackNavigator?.navigateBack(result = true)
    }
}

data class AccountDetailsScreenNavArgs(
    val accountId: String,
)
