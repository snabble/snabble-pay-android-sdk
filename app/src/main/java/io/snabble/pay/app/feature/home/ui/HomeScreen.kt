package io.snabble.pay.app.feature.home.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.R
import io.snabble.pay.app.data.utils.ErrorResponse
import io.snabble.pay.app.feature.destinations.AccountDetailsScreenDestination
import io.snabble.pay.app.feature.detailsaccount.ui.AccountDetailsScreenNavArgs
import io.snabble.pay.app.feature.home.AddNewCart
import io.snabble.pay.app.feature.home.HomeViewModel
import io.snabble.pay.app.feature.home.Loading
import io.snabble.pay.app.feature.home.ShowAccounts
import io.snabble.pay.app.feature.home.ui.widget.DemoCard
import io.snabble.pay.app.ui.widgets.AlertWidget
import io.snabble.pay.app.ui.widgets.accountcard.AccountCardPager
import io.snabble.pay.app.utils.browseUrl
import io.snabble.pay.core.Reason

@RequiresApi(Build.VERSION_CODES.O)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator?,
) {
    LocalLifecycleOwner.current.lifecycle.addObserver(homeViewModel)
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        homeViewModel.validationLink.collect {
            context.browseUrl(it)
        }
    }
    val openDialog = remember {
        mutableStateOf(false)
    }
    val error = homeViewModel.error.collectAsState(null)

    LaunchedEffect(Unit) {
        homeViewModel.error.collect {
            if (it?.reason == Reason.MANDATE_NOT_ACCEPTED) {
                Toast
                    .makeText(
                        context,
                        context.getText(R.string.mandate_required),
                        Toast.LENGTH_SHORT
                    )
                    .show()
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

    val uiState = homeViewModel.uiState.collectAsState()
    when (val state = uiState.value) {
        Loading -> Home(
            cardComposable = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            onFloatingActionButtonClick = homeViewModel::getValidationLink
        )
        is ShowAccounts -> Home(
            cardComposable = {
                AccountCardPager(
                    modifier = Modifier,
                    accountCardList = state.accountCards,
                    onCurrentPage = { accountId ->
                        homeViewModel.setActiveAccountCard(accountId.trimIndent())
                    }
                ) { accountCard ->
                    navigator?.navigate(
                        AccountDetailsScreenDestination(
                            navArgs = AccountDetailsScreenNavArgs(
                                accountId = accountCard.accountId
                            )
                        )
                    )
                }
            },
            onFloatingActionButtonClick = homeViewModel::getValidationLink
        )
        AddNewCart -> Home(
            cardComposable = {
                DemoCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            },
            onFloatingActionButtonClick = homeViewModel::getValidationLink
        )
    }
}
