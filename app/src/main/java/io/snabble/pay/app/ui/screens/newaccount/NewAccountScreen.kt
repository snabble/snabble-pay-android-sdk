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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.screens.destinations.HomeScreenDestination
import io.snabble.pay.app.ui.screens.destinations.VerifyAccountScreenDestination
import io.snabble.pay.app.ui.screens.detailsaccount.Loading
import io.snabble.pay.app.ui.screens.detailsaccount.ShowAccount
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.AccountInformation
import io.snabble.pay.app.ui.widgets.EditTextField
import io.snabble.pay.app.ui.widgets.InfoText

@Destination
@Composable
fun NewAccountScreen(
    navigator: DestinationsNavigator?,
    newAccountViewModel: NewAccountViewModel = hiltViewModel(),
) {

    val state = newAccountViewModel.uiState.collectAsState()

    var cardName by rememberSaveable { mutableStateOf("") }

    cardName = when (val it = state.value) {
        is Loading -> {
            it.name
        }
        is ShowAccount -> it.accountCardModel.name
    }

    val (id, holderName, iban, bank) = when (val it = state.value) {
        is Loading -> {
            listOf("", "", "", "")
        }
        is ShowAccount -> {
            val card = it.accountCardModel
            listOf(card.accountId, card.holderName, card.iban, card.bank)
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
                        if (id.isNotEmpty()) {
                            newAccountViewModel.updateAccountName(
                                id,
                                cardName
                            )
                        }
                    }
                )
                AccountInformation(
                    holderName = holderName,
                    iban = iban,
                    bank = bank
                )
                Spacer(modifier = Modifier.height(96.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "SEPA-Lastschriftmandat",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    InfoText(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        string = "Ich ermächtige Snabble Pay die Zahlungen von " +
                            "meinem Konto mittels Lastschrift einzuziehen."
                    )
                }
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
                        text = "Zustimmen und loslegen"
                    )
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
