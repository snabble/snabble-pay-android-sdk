package io.snabble.pay.app.ui.screens.detailsaccountscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.R
import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.EditTextField
import io.snabble.pay.app.ui.widgets.accountcard.AccountCard

@Destination
@Composable
fun DetailsAccountScreen(
    navigator: DestinationsNavigator?,
    accountCardModel: AccountCardModel,
) {
    AppBarLayout(
        title = "",
        icon = Icons.Filled.Clear,
        navigator = navigator
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (backgroundOne, backgroundTwo) = createRefs()
                val (edit, div, card, mandate, button) = createRefs()

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(backgroundOne) {
                            top.linkTo(card.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(backgroundTwo.top)
                        },
                    contentScale = ContentScale.FillWidth,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_ellipse),
                    contentDescription = ""
                )
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(backgroundTwo) {
                            top.linkTo(backgroundOne.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    contentScale = ContentScale.FillWidth,
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_background),
                    contentDescription = ""
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
                    placeholder = accountCardModel.name,
                    value = "",
                    onValueChange = {}
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .constrainAs(div) {
                            top.linkTo(edit.bottom)
                            linkTo(parent.start, parent.end)
                        }
                )
                AccountCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .constrainAs(card) {
                            top.linkTo(div.bottom)
                            linkTo(parent.start, parent.end)
                        },
                    accountCard = accountCardModel,
                    onClick = {},
                    qrCodeString = "https://www.google.com/"
                )
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp)
                        .constrainAs(mandate) {
                            top.linkTo(card.bottom)
                            linkTo(parent.start, parent.end)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 60.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.defaultMinSize(
                                minWidth = 40.dp,
                                minHeight = 40.dp
                            ),
                            shape = CircleShape,
                            color = colorResource(id = R.color.gray)
                        ) {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Filled.Check,
                                contentDescription = ""
                            )
                        }
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Sepa Mandat erteilt"
                        )
                    }
                }
                TextButton(
                    modifier = Modifier
                        .height(40.dp)
                        .constrainAs(button) {
                            linkTo(mandate.bottom, parent.bottom, bottomMargin = 32.dp, bias = 1.0f)
                            linkTo(parent.start, parent.end)
                        },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colorResource(id = R.color.gray),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_delete),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Bankverbindung löschen"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsAccountScreenPreview() {
    SnabblePayTheme {
        DetailsAccountScreen(
            navigator = null,
            accountCardModel = AccountCardModel(
                cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
                qrCodeToken = "test",
                holderName = "Petra MusterMann",
                accountId = "1",
                name = "Mein Konto",
                iban = "DE91 1000 0000 0123 4567 89",
                bank = "Deutsche Bank"
            )
        )
    }
}
