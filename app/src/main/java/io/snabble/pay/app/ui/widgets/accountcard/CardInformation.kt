package io.snabble.pay.app.ui.widgets.accountcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CardInformation(
    modifier: Modifier = Modifier,
    holderName: String,
    iban: String,
    customCardName: String,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = iban.toIban(),
            color = Black,
            fontWeight = Bold,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Start

        )
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = holderName,
                color = Black,
                textAlign = TextAlign.Start,
                fontWeight = Bold,
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                modifier = Modifier.weight(1f),
                text = customCardName,
                color = Black,
                fontWeight = Bold,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.End
            )
        }
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
        customCardName = "Mustermann Bank"
    )
}

fun String.toIban(): String {
    var iban = ""
    forEachIndexed { index, c ->
        iban = if (index % TARGET_MOD == TARGET_INDEX) {
            "$iban "
        } else {
            "$iban$c"
        }
    }
    return iban
}

private const val TARGET_INDEX = 4
private const val TARGET_MOD = 5
