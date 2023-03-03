package io.snabble.pay.app.ui.accountscreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun InfoText(
    modifier: Modifier = Modifier,
    string: String,
) {
    Text(
        modifier = modifier,
        text = string,
        letterSpacing = 1.5.sp,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center
    )
}

@Preview(
    showBackground = true
)
@Composable
fun InfoTextPreview() {
    InfoText(string = "This is a info text")
}
