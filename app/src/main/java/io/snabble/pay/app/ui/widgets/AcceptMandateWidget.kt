package io.snabble.pay.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AcceptMandateWidget(
    modifier: Modifier,
    spacer: @Composable () -> Unit,
    onAccept: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "SEPA-Lastschriftmandat",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoText(
            modifier = Modifier.padding(horizontal = 16.dp),
            string = "Ich ermächtige Snabble Pay die Zahlungen von " +
                "meinem Konto mittels Lastschrift einzuziehen."
        )
    }
    spacer()
    DefaultButton(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .height(40.dp),
        text = "Zustimmen und loslegen"
    ) {
        onAccept()
    }
}
