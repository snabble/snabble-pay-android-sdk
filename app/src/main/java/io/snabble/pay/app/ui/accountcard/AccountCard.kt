package io.snabble.pay.app.ui.accountcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.snabble.pay.app.domain.accountCard.AccountCardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    viewModel: AccountCardViewModel = AccountCardViewModel(),
) {

    val accountCard = viewModel.accountCard
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier),
        onClick = { /*TODO*/ }) {

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
                    .padding(horizontal = 16.dp)
                    .background(color = Color.Transparent)
                    .fillMaxWidth()
            ) {
                val (qrCode, accInf) = this.createRefs()
                Image(
                    modifier = Modifier
                        .widthIn(max = 120.dp)
                        .heightIn(max = 120.dp)
                        .padding(top = 16.dp, bottom = 16.dp)
                        .constrainAs(qrCode) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    bitmap = accountCard.qrCodeToken.asImageBitmap(),
                    contentDescription = ""
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

@Preview
@Composable

fun PreviewAccountCard() {
    AccountCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}
