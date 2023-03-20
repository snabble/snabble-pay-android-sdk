package io.snabble.pay.app.feature.newaccount.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.feature.destinations.HomeScreenDestination
import io.snabble.pay.app.feature.destinations.VerifyAccountScreenDestination
import io.snabble.pay.app.feature.newaccount.Error
import io.snabble.pay.app.feature.newaccount.Loading
import io.snabble.pay.app.feature.newaccount.MandateAccepted
import io.snabble.pay.app.feature.newaccount.MandatePendingOrDeclined
import io.snabble.pay.app.feature.newaccount.NewAccountViewModel
import io.snabble.pay.app.feature.newaccount.ShowAccount
import io.snabble.pay.app.feature.newaccount.ui.widget.AccountInformation
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.AcceptMandateWidget
import io.snabble.pay.app.ui.widgets.DefaultButton
import io.snabble.pay.app.ui.widgets.EditTextField

@Destination
@Composable
fun NewAccountScreen(
    navigator: DestinationsNavigator?,
    newAccountViewModel: NewAccountViewModel = hiltViewModel(),
) {
    val uiState = newAccountViewModel.uiState.collectAsState()

    var cardName by rememberSaveable { mutableStateOf("") }
    var accountCard: AccountCard? by rememberSaveable { mutableStateOf(null) }

    when (val it = uiState.value) {
        Loading -> Unit // TODO

        is ShowAccount -> {
            cardName = it.accountCard.name
            accountCard = it.accountCard
        }

        MandateAccepted -> Unit // TODO

        MandatePendingOrDeclined -> Unit // TODO

        is Error -> {
            Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    AppBarLayout(
        title = "Bankkonto",
        onBackClick = {
            navigator?.navigate(VerifyAccountScreenDestination)
        }
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
                            cardName,
                            it.cardBackgroundColor
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
            accountCard?.let { accountCard ->
                if (uiState.value is MandatePendingOrDeclined) {
                    AcceptMandateWidget(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp),
                        spacer = { Spacer(modifier = Modifier.weight(1f)) },
                        textColor = MaterialTheme.colorScheme.onSurface,
                        onAccept = {
                            newAccountViewModel.acceptMandate(accountCard.accountId)
                            navigator?.navigate(HomeScreenDestination)
                        }
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                    DefaultButton(
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .height(40.dp),
                        text = "Abschließen"
                    ) {
                        navigator?.navigate(HomeScreenDestination)
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
