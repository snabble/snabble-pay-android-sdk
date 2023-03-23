package io.snabble.pay.app.feature.newaccount.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TableRow(
    modifier: Modifier = Modifier,
    descriptor: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .defaultMinSize(
                minHeight = TextFieldDefaults.MinHeight
            )
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = descriptor,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            modifier = Modifier
                .weight(2f)
                .padding(start = 8.dp),
            text = value,
            color = Color.Gray,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun TableRowPreview() {
    TableRow(
        descriptor = "Bank",
        value = "Muster Bank"
    )
}
