package io.snabble.pay.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun AccountInformation(
    modifier: Modifier = Modifier,
    holderName: String,
    iban: String,
    bank: String,
) {
    Column(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Divider()
            TableRow(descriptor = "KontoInhaber", value = holderName)
            Divider()
            TableRow(descriptor = "IBAN", value = iban)
            Divider()
            TableRow(descriptor = "Bank", value = bank)
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun NewAccountWidgetPreview() {
    SnabblePayTheme {
        AccountInformation(
            holderName = "Max Mustermann",
            iban = "DE12 1234 1234 1234 1234 12",
            bank = "Muster Bank"
        )
    }
}
