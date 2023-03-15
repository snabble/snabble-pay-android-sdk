package io.snabble.pay.app.feature.detailsaccount.ui

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.data.viewModelStates.Error
import io.snabble.pay.app.data.viewModelStates.Loading
import io.snabble.pay.app.data.viewModelStates.ShowAccount
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.feature.detailsaccount.DetailsAccountViewModel
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DeleteButton
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DetailsBackground
import io.snabble.pay.app.feature.detailsaccount.ui.widget.MandateGranted
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.AcceptMandateWidget
import io.snabble.pay.app.ui.widgets.EditTextField
import io.snabble.pay.app.ui.widgets.accountcard.AccountCard

@Destination
@Composable
fun DetailsAccountScreen(
    navigator: DestinationsNavigator?,
    detailsAccountViewModel: DetailsAccountViewModel = hiltViewModel(),
    accountId: String,
) {
    var cardName by rememberSaveable { mutableStateOf("") }
    var accountCard: AccountCard? by rememberSaveable { mutableStateOf(null) }

    val uiState = detailsAccountViewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is Loading -> detailsAccountViewModel.getAccount(accountId)
        is ShowAccount -> {
            cardName = state.accountCard.name
            accountCard = state.accountCard
        }
        is Error -> {
            Toast.makeText(LocalContext.current, state.message, Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }

    AppBarLayout(
        title = "",
        icon = Icons.Filled.Clear,
        onBackClick = { navigator?.navigateUp() }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val background = createRef()
            val (edit, div, card, mandate, button) = createRefs()

            DetailsBackground(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(background) {
                        top.linkTo(card.top)
                        linkTo(start = parent.start, end = parent.end)
                    }
            )
            EditTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(edit) {
                        top.linkTo(parent.top, margin = 40.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                placeholder = cardName,
                value = cardName,
                onValueChange = {
                    cardName = it
                },
                onAction = {
                    detailsAccountViewModel.updateAccountName(
                        accountId,
                        cardName
                    )
                }
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .constrainAs(div) {
                        top.linkTo(edit.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            accountCard?.let { accountCard ->
                AccountCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .constrainAs(card) {
                            top.linkTo(div.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    accountCard = accountCard,
                    onClick = {},
                    qrCodeString = "https://www.google.com/"
                )
                if (accountCard.mandateState == MandateState.ACCEPTED) {
                    MandateGranted(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .constrainAs(mandate) {
                                top.linkTo(card.bottom, 16.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                } else {
                    ElevatedCard(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .constrainAs(mandate) {
                                top.linkTo(card.bottom, 32.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        AcceptMandateWidget(
                            modifier = Modifier.padding(vertical = 16.dp),
                            spacer = { Spacer(modifier = Modifier.height(32.dp)) },
                            onAccept = {
                                detailsAccountViewModel.acceptMandate(accountId)
                            }

                        )
                    }
                }
            }
            DeleteButton(
                modifier = Modifier
                    .height(40.dp)
                    .constrainAs(button) {
                        linkTo(mandate.bottom, parent.bottom, bottomMargin = 32.dp, bias = 1.0f)
                        linkTo(parent.start, parent.end)
                    },
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun DetailsAccountScreenPreview() {
    SnabblePayTheme {
        DetailsAccountScreen(
            navigator = null,
            accountId = ""
        )
    }
}
