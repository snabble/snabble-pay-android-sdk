package io.snabble.pay.app.feature.detailsaccount.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.R
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.utils.GradiantGenerator
import io.snabble.pay.app.feature.destinations.HomeScreenDestination
import io.snabble.pay.app.feature.destinations.NewAccountScreenDestination
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DeleteButton
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DetailsBackground
import io.snabble.pay.app.feature.detailsaccount.ui.widget.EditTextFieldCentered
import io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate.MandateState
import io.snabble.pay.app.feature.newaccount.ui.NewAccountScreenNavArgs
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.SnabblePayDialog
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.accountcard.AccountCard
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.shared.account.domain.model.Account

@Composable
fun AccountDetails(
    navigator: DestinationsNavigator?,
    mandate: Mandate?,
    accountCard: AccountCard,
    onLabelChange: (label: String, colors: List<String>) -> Unit,
    onDeleteAccount: () -> Unit,
) {
    val showChangeTitleDialog = rememberSaveable {
        mutableStateOf(false)
    }

    val cardName = rememberSaveable(inputs = arrayOf(accountCard.name)) {
        mutableStateOf(accountCard.name)
    }
    val showDeleteDialog = remember { mutableStateOf(false) }
    SnabblePayTheme {
        AppBarLayout(
            title = accountCard.name,
            onBackClick = {
                navigator?.navigate(HomeScreenDestination)
            }
        ) {
            if (showChangeTitleDialog.value) {
                SnabblePayDialog(
                    dialogTitle = stringResource(id = R.string.account_details_change_title),
                    secondaryButtonLabel = stringResource(id = R.string.change_title_dialog_button),
                    onSecondaryClick = {
                        onLabelChange(cardName.value, accountCard.cardBackgroundColor)
                        showChangeTitleDialog.value = false
                    },
                    onDismiss = {
                        showChangeTitleDialog.value = false
                    }
                ) {
                    EditTextFieldCentered(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = accountCard.name,
                        onValueChange = { cardName.value = it },
                        onAction = {
                            cardName.value = it
                            onLabelChange(it, accountCard.cardBackgroundColor)
                            showChangeTitleDialog.value = false
                        }
                    )
                }
            }
            if (showDeleteDialog.value) {
                SnabblePayDialog(
                    dialogTitle = stringResource(id = R.string.delete_card_title),
                    message = stringResource(id = R.string.delete_card_message),
                    primaryButtonLabel = stringResource(id = R.string.delete_card_confirm),
                    onPrimaryClick = {
                        onDeleteAccount()
                        showDeleteDialog.value = false
                    },
                    secondaryButtonLabel = stringResource(id = R.string.delete_card_cancel),
                    onSecondaryClick = { showDeleteDialog.value = false },
                    onDismiss = { showDeleteDialog.value = false }
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(32.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    DetailsBackground(modifier = Modifier.fillMaxWidth())
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        AccountCard(
                            accountCard = accountCard,
                            onClick = {
                                navigator?.navigate(
                                    NewAccountScreenDestination(
                                        navArgs = NewAccountScreenNavArgs(
                                            accountCard.accountId
                                        )
                                    )
                                )
                            },
                            qrCodeString = accountCard.sessionToken?.value
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MandateState(
                            mandateState = mandate?.state ?: Mandate.State.PENDING,
                            mandateNumber = mandate?.id ?: ""
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        OutlinedButton(
                            enabled = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(32.dp),
                            border = BorderStroke(1.dp, Color.Black),
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = Color.Transparent
                            ),
                            onClick = { showChangeTitleDialog.value = true }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "",
                                tint = Color.Black
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = stringResource(id = R.string.account_details_change_title),
                                color = Color.Black,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        DeleteButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { showDeleteDialog.value = true }
                        )
                    }
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
        sessionToken = null,
        holderName = "Muster Mann",
        accountId = "1",
        mandateState = Account.MandateState.ACCEPTED,
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
            " et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata " +
            "sanctus est Lorem ipsum dolor sit amet.",
        state = Mandate.State.PENDING
    )

    SnabblePayTheme {
        AccountDetails(
            navigator = null,
            mandate = mandate,
            accountCard = card,
            onLabelChange = { _, _ -> },
            onDeleteAccount = {}
        )
    }
}
