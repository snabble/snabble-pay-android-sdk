package io.snabble.pay.app.ui.widgets

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun HyperLinkText(
    modifier: Modifier = Modifier,
    string: String,
    onClick: () -> Unit,
) {
    ClickableText(
        modifier = modifier,
        text = AnnotatedString(
            text = string,
            spanStyle = SpanStyle(
                color = MaterialTheme.colorScheme.onPrimary
            )
        ),
        style = MaterialTheme.typography.bodyMedium,
        onClick = { onClick() }
    )
}

@Preview(
    showBackground = true
)
@Composable
fun HyperLinkTextPreview() {
    SnabblePayTheme {
        HyperLinkText(
            string = "Demo Link"
        ) {}
    }
}
