package io.snabble.pay.app.ui.accountcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    accountCard: AccountCardModel,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier),
        onClick = { /*TODO*/ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = accountCard.cardBackgroundColor
                    )
                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(color = Color.Transparent)
            ) {
                val (qrCode, accInf) = this.createRefs()

                QrCodeImage(
                    modifier = Modifier
                        .widthIn(max = 130.dp)
                        .heightIn(max = 130.dp)
                        .padding(top = 16.dp, bottom = 16.dp)
                        .constrainAs(qrCode) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    qrCodeToken = accountCard.qrCodeToken
                )

                AccountInformation(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .constrainAs(accInf) {
                            top.linkTo(qrCode.bottom)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                        },
                    holderName = accountCard.holderName,
                    iban = accountCard.iban,
                    bank = accountCard.bank
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewAccountCard() {
    AccountCard(
        modifier = Modifier
            .padding(horizontal = 32.dp),
        accountCard = AccountCardModel(
            cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
            qrCodeToken = "https://www.google.com/",
            holderName = "Muster Mann",
            iban = "DE 1234 1234 1234 1234",
            bank = "Deutsche Bank"
        )
    )
}
