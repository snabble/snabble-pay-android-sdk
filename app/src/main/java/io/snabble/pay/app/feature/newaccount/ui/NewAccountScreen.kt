package io.snabble.pay.app.feature.newaccount.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.feature.detailsaccount.ui.LoadingScreen
import io.snabble.pay.app.feature.newaccount.Loading
import io.snabble.pay.app.feature.newaccount.NewAccount
import io.snabble.pay.app.feature.newaccount.NewAccountViewModel
import io.snabble.pay.app.feature.newaccount.ShowAccount
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.AlertWidget

@Destination(
    navArgsDelegate = NewAccountScreenNavArgs::class,
    deepLinks = [
        DeepLink(uriPattern = "snabble-pay://account/check?accountId={accountId}")
    ]
)
@Composable
fun NewAccountScreen(
    navigator: DestinationsNavigator?,
    viewModel: NewAccountViewModel = hiltViewModel(),
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
        when (val payError = error.value) {
            is ErrorResponse -> AlertWidget(
                payError = payError,
                onDismiss = { openDialog.value = false }
            )
        }
    }
    val uiState = viewModel.uiState.collectAsState()

    when (val it = uiState.value) {
        Loading -> LoadingScreen(navigator = navigator)

        is ShowAccount -> {
            NewAccount(
                navigator = navigator,
                mandate = it.mandate,
                accountCard = it.accountCard,
                onLabelChange = viewModel::updateAccountName,
                onMandateAccept = viewModel::acceptMandate
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun NewAccountScreenPreview() {
    SnabblePayTheme {
        NewAccountScreen(navigator = null)
    }
}

data class NewAccountScreenNavArgs(
    val accountId: String,
)
