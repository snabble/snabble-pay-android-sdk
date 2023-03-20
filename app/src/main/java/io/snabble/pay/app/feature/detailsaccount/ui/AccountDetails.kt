package io.snabble.pay.app.feature.detailsaccount.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.utils.GradiantGenerator
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DeleteButton
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DetailsBackground
import io.snabble.pay.app.feature.detailsaccount.ui.widget.MandateGranted
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.AcceptMandateWidget
import io.snabble.pay.app.ui.widgets.EditTextField
import io.snabble.pay.app.ui.widgets.accountcard.AccountCard

@Composable
fun AccountDetails(
    navigator: DestinationsNavigator?,
    isMandateAccepted: Boolean,
    accountCard: AccountCard,
    onLabelChange: (label: String, colors: List<String>) -> Unit,
    onMandateAccept: () -> Unit,
    onDeleteAccount: () -> Unit,
) {
    var cardName by rememberSaveable { mutableStateOf(accountCard.name) }

    AppBarLayout(
        title = "",
        icon = Icons.Filled.Clear,
        onBackClick = {
            navigator?.navigateUp()
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(40.dp))
            EditTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = cardName,
                value = cardName,
                onValueChange = { cardName = it },
                onAction = { onLabelChange(cardName, accountCard.cardBackgroundColor) }
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                DetailsBackground(modifier = Modifier.fillMaxWidth())
                Column(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .weight(1f)
                    ) {
                        AccountCard(
                            accountCard = accountCard,
                            onClick = {},
                            qrCodeString = "https://www.google.com/"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Mandate(
                            isMandateAccepted,
                            onMandateAccept = { onMandateAccept() }
                        )
                    }

                    DeleteButton(
                        modifier = Modifier
                            .height(40.dp)
                            .align(CenterHorizontally),
                        onClick = { onDeleteAccount() }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun Mandate(
    isMandateAccepted: Boolean,
    onMandateAccept: () -> Unit,
) {
    if (isMandateAccepted) {
        MandateGranted(modifier = Modifier.fillMaxWidth())
    } else {
        ElevatedCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            AcceptMandateWidget(
                modifier = Modifier.padding(vertical = 16.dp),
                spacer = { Spacer(modifier = Modifier.height(32.dp)) },
                onAccept = { onMandateAccept() }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AccountDetailsPreview() {
    val card = AccountCard(
        cardBackgroundColor = GradiantGenerator.createGradiantColorList(),
        qrCodeToken = "https://www.google.com/",
        holderName = "Muster Mann",
        accountId = "1",
        mandateState = MandateState.ACCEPTED,
        iban = "DE 1234 1234 1234 1234",
        bank = "Deutsche Bank",
        name = "Mein Konto"
    )

    SnabblePayTheme {
        AccountDetails(
            navigator = null,
            isMandateAccepted = false,
            accountCard = card,
            onLabelChange = {_,_->},
            onMandateAccept = {},
            onDeleteAccount = {}
        )
    }
}
