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
) {
    Column(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Divider()
            TableRow(descriptor = "KontoInhaber", value = "Peter Mustermann")
            Divider()
            TableRow(descriptor = "IBAN", value = "DE12 3456 7891 0111 2131 41")
            Divider()
            TableRow(descriptor = "Bank", value = "Muster Bank")
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun NewAccountWidgetPreview() {
    SnabblePayTheme {
        AccountInformation()
    }
}
