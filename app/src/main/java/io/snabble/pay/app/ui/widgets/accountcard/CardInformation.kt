package io.snabble.pay.app.ui.widgets.accountcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CardInformation(
    modifier: Modifier = Modifier,
    holderName: String,
    iban: String,
    bank: String,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = holderName,
            color = Black,
            fontWeight = Bold,
            style = MaterialTheme.typography.bodySmall,
            letterSpacing = 1.5.sp,
            textAlign = TextAlign.Start

        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = iban,
            color = Black,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start

        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = bank,
            color = Black,
            fontWeight = Bold,
            style = MaterialTheme.typography.bodySmall,
            letterSpacing = 1.5.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AccountInformationPreview() {
    CardInformation(
        holderName = "Max Mustermann",
        iban = "DE12 3456 7891 0112 13",
        bank = "Mustermann Bank"
    )
}
