package io.snabble.pay.app.ui.widgets.accountcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.account.domain.model.MandateState
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.domain.account.utils.GradiantGenerator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    accountCard: AccountCard,
    qrCodeString: String?,
    useLabel: Boolean = false,
    onClick: (AccountCard) -> Unit,
) {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = accountCard.cardBackgroundColor.toColorList()
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            QrCodeImage(
                modifier = Modifier
                    .size(width = 100.dp, height = 100.dp),
                qrCodeToken = qrCodeString
            )
            Spacer(modifier = Modifier.height(16.dp))
            CardInformation(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                holderName = if (useLabel) accountCard.name else accountCard.holderName,
                iban = accountCard.iban,
                bank = accountCard.bank
            )
            Spacer(modifier = Modifier.height(20.dp))
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
            sessionToken = null,
            holderName = "Muster Mann",
            accountId = "1",
            mandateState = MandateState.ACCEPTED,
            iban = "DE 1234 1234 1234 1234",
            bank = "Deutsche Bank",
            name = "Mein Konto"
        ),
        onClick = {},
        qrCodeString = "https://www.google.com/"
    )
}

fun List<String>.toColorList() =
    map { Color(android.graphics.Color.parseColor(it)) }
