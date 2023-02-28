package io.snabble.pay.app.ui.accountcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.snabble.pay.app.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    accountCard: AccountCard,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .wrapContentHeight()
            .then(modifier),
        onClick = { /*TODO*/ }) {

        ConstraintLayout(
            modifier = Modifier
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
                bitmap = QrCodeGenerator.generateQrCode(accountCard.qrCodeToken).asImageBitmap(),
                contentDescription = ""
            )
            AccountInformation(
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp)
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

@Composable
fun AccountInformation(
    modifier: Modifier = Modifier,
    holderName: String,
    iban: String,
    bank: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            text = holderName,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = iban,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = bank,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable

fun PreviewAccountCard() {
    AccountCard(
        accountCard = AccountCard(
            qrCodeToken = "Test String",
            holderName = "Petra Mustermann",
            iban = "DE91 1000 0000 0123 4567 89",
            bank = "Deutsche Bank",
        )
    )
}

data class AccountCard(
    val qrCodeToken: String,
    val holderName: String,
    val iban: String,
    val bank: String,

    )