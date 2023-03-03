package io.snabble.pay.app.ui.screens.accountscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    accountCardModel: AccountCardModel = AccountCardModel(
        cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
        qrCodeToken = "https://www.google.com/",
        holderName = "Muster Mann",
        iban = "DE 1234 1234 1234 1234",
        bank = "Deutsche Bank"
    ),
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
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_ellipse),
                        contentDescription = ""
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_background),
                        contentDescription = ""
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EditTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        placeholder = "Mein Bankkonto",
                        value = "",
                        onValueChange = {}
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    AccountCard(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp),
                        accountCard = accountCardModel,
                        navigator = navigator
                    )
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(horizontal = 16.dp),
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
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        modifier = Modifier
                            .padding(bottom = 32.dp)
                            .height(40.dp),
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
                iban = "DE91 1000 0000 0123 4567 89",
                bank = "Deutsche Bank"
            )
        )
    }
}
