package io.snabble.pay.app.feature.newaccount.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R
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
            TableRow(descriptor = stringResource(id = R.string.new_account_owner), value = holderName)
            Divider(color = DividerDefaults.color)
            TableRow(descriptor = "IBAN", value = iban)
            Divider(color = DividerDefaults.color)
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
