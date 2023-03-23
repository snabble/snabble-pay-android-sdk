package io.snabble.pay.app.feature.newaccount.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun DefaultButton(
    modifier: Modifier,
    text: String,
    onclick: () -> Unit,
) {
    ElevatedButton(
        modifier = modifier,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = { onclick() }
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text
        )
    }
}

@Preview
@Composable
fun DefaulButtonPreview() {
    SnabblePayTheme {
        DefaultButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Demo Button"
        ) {
        }
    }
}
