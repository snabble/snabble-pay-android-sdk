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
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.utils.GradiantGenerator
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DeleteButton
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DetailsBackground
import io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate.Mandate
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.EditTextField
import io.snabble.pay.app.ui.widgets.accountcard.AccountCard
import io.snabble.pay.mandate.domain.model.Mandate

@Composable
fun AccountDetails(
    navigator: DestinationsNavigator?,
    mandate: Mandate?,
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
                            modifier = Modifier,
                            mandate = mandate,
                            onAccept = { onMandateAccept() }
                        )
                    }

//                    DeleteButton(
//                        modifier = Modifier
//                            .height(40.dp)
//                            .align(CenterHorizontally),
//                        onClick = { onDeleteAccount() }
//                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
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

    val mandate = Mandate(
        id = "",
        htmlText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam " +
            "nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam " +
            "voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd " +
            "gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum " +
            "dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt " +
            "ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam" +
            " et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus" +
            " est Lorem ipsum dolor sit amet.",
        state = io.snabble.pay.mandate.domain.model.MandateState.PENDING
    )

    SnabblePayTheme {
        AccountDetails(
            navigator = null,
            mandate = mandate,
            accountCard = card,
            onLabelChange = { _, _ -> },
            onMandateAccept = {},
            onDeleteAccount = {}
        )
    }
}
