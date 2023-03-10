package io.snabble.pay.app.ui.screens.newaccount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.domain.account.AccountCardModel
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.screens.destinations.HomeScreenDestination
import io.snabble.pay.app.ui.screens.destinations.VerifyAccountScreenDestination
import io.snabble.pay.app.ui.screens.detailsaccount.Loading
import io.snabble.pay.app.ui.screens.detailsaccount.ShowAccount
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.AccountInformation
import io.snabble.pay.app.ui.widgets.EditTextField
import io.snabble.pay.app.ui.widgets.MandateWidget

@Destination
@Composable
fun NewAccountScreen(
    navigator: DestinationsNavigator?,
    newAccountViewModel: NewAccountViewModel = hiltViewModel(),
) {
    val uiState = newAccountViewModel.uiState.collectAsState()
    val mandate = newAccountViewModel.mandate.collectAsState()

    var cardName by rememberSaveable { mutableStateOf("") }

    cardName = when (val it = uiState.value) {
        is Loading -> it.name
        is ShowAccount -> it.accountCardModel.name
    }

    val accountCard: AccountCardModel? = when (val it = uiState.value) {
        is Loading -> null
        is ShowAccount -> {
            newAccountViewModel.createMandate(it.accountCardModel.accountId)
            it.accountCardModel
        }
    }

    AppBarLayout(
        title = "Bankkonto",
        onBackClick = { navigator?.navigate(VerifyAccountScreenDestination) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    placeholder = cardName,
                    value = cardName,
                    onValueChange = {
                        cardName = it
                    },
                    onAction = {
                        accountCard?.let {
                            newAccountViewModel.updateAccountName(
                                it.accountId,
                                cardName
                            )
                        }
                    }
                )
                AccountInformation(
                    holderName = accountCard?.holderName ?: "",
                    iban = accountCard?.iban ?: "",
                    bank = accountCard?.bank ?: ""
                )
                Spacer(modifier = Modifier.height(96.dp))
                val newMandate = mandate.value
                if (newMandate != null && newMandate.state.name != "ACCEPTED") {
                    MandateWidget(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        spacer = { Spacer(modifier = Modifier.weight(1f)) },
                        onAccept = {
                            if (accountCard != null) {
                                newAccountViewModel.acceptMandate(
                                    accountCard.accountId,
                                    newMandate.id
                                )
                            }
                            navigator?.navigate(HomeScreenDestination)
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                    ElevatedButton(
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        onClick = {
                            navigator?.navigate(HomeScreenDestination)
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Abschließen"
                        )
                    }
                }
            }
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
