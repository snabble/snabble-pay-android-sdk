package io.snabble.pay.app.ui.accountcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign

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
            modifier = Modifier.fillMaxWidth(),
            text = holderName,
            color = Black,
            fontWeight = Bold,
            style = MaterialTheme.typography.bodySmall,
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
            textAlign = TextAlign.Start
        )
    }
}