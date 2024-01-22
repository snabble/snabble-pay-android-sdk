package io.snabble.pay.app.feature.detailsaccount.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import io.snabble.pay.app.R
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.feature.destinations.HomeScreenDestination
import io.snabble.pay.app.feature.detailsaccount.DetailsAccountViewModel
import io.snabble.pay.app.ui.widgets.AlertWidget
import io.snabble.pay.core.Reason

@Destination(
    navArgsDelegate = AccountDetailsScreenNavArgs::class
)
@Composable
fun AccountDetailsScreen(
    viewModel: DetailsAccountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator?,
) {
    LocalLifecycleOwner.current.lifecycle.addObserver(viewModel)
    val openDialog = remember {
        mutableStateOf(false)
    }
    val error = viewModel.error.collectAsState(null)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.error.collect {
            if (it?.reason == Reason.MANDATE_NOT_ACCEPTED) {
                Toast.makeText(
                    context,
                    context.getText(R.string.mandate_required),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                openDialog.value = true
            }
        }
    }

    if (openDialog.value) {
        when (val payError = error.value) {
            is ErrorResponse -> AlertWidget(
                payError = payError,
                onDismiss = { openDialog.value = false }
            )
        }
    }

    val uiState = viewModel.uiState.collectAsState().value

    when {
        uiState.isLoading -> LoadingScreen(navigator = navigator)
        uiState.accountCard != null -> AccountDetails(
            navigator = navigator,
            mandate = uiState.mandate,
            accountCard = uiState.accountCard,
            onLabelChange = viewModel::updateAccountName,
            onDeleteAccount = viewModel::deleteAccount
        )
        uiState.isAccountDeleted -> navigator?.navigate(HomeScreenDestination) {
            popUpTo(HomeScreenDestination) {
                inclusive = true
            }
        }
    }
}

data class AccountDetailsScreenNavArgs(
    val accountId: String,
)
