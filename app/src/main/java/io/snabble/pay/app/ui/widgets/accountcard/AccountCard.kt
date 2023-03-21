package io.snabble.pay.app.ui.widgets.accountcard

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.utils.GradiantGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    accountCard: AccountCard,
    qrCodeString: String?,
    onDelete: () -> Unit,
    isEditable: Boolean,
    onClick: (AccountCard) -> Unit,
) {
    Log.d("xx", "AccountCard: $isEditable ")
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier),
        onClick = { onClick(accountCard) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = accountCard.cardBackgroundColor.toColorList()
                    )
                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(color = Color.Transparent)
            ) {
                val (qrCode, accInf, delete) = this.createRefs()
                if (isEditable) {
                    Icon(
                        modifier = Modifier
                            .constrainAs(delete) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top, margin = 16.dp)
                            }
                            .clickable {
                                onDelete()
                            },
                        imageVector = Icons.Filled.Delete,
                        contentDescription = ""
                    )
                }
                QrCodeImage(
                    modifier = Modifier
                        .widthIn(max = 130.dp, min = 130.dp)
                        .heightIn(max = 130.dp, min = 130.dp)
                        .padding(top = 16.dp, bottom = 16.dp)
                        .constrainAs(qrCode) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    qrCodeToken = qrCodeString
                )
                CardInformation(
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
        accountCard = AccountCard(
            cardBackgroundColor = GradiantGenerator.createGradiantColorList(),
            qrCodeToken = "https://www.google.com/",
            holderName = "Muster Mann",
            accountId = "1",
            mandateState = MandateState.ACCEPTED,
            iban = "DE 1234 1234 1234 1234",
            bank = "Deutsche Bank",
            name = "Mein Konto"
        ),
        onClick = {},
        qrCodeString = "https://www.google.com/",
        isEditable = false,
        onDelete = {}
    )
}

fun List<String>.toColorList() =
    map { Color(android.graphics.Color.parseColor(it)) }