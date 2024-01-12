package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun EditTextFieldCentered(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    onAction: () -> Unit,
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.onSurface,
        backgroundColor = Transparent
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors
    ) {
        TextField(
            modifier = modifier,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                disabledIndicatorColor = Transparent,
                unfocusedIndicatorColor = DividerDefaults.color
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onAction()
                }
            ),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            maxLines = 1,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                letterSpacing = 0.1.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun EditTextFieldPreview() {
    EditTextFieldCentered(
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {},
        onAction = {}
    )
}
