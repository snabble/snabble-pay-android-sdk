package io.snabble.pay.app.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = placeholder
            )
        },
        trailingIcon = {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.Outlined.Edit,
                contentDescription = ""
            )
        },
        value = value,
        onValueChange = {
            onValueChange(it)
        }
    )
}

@Preview(
    showBackground = true
)
@Composable
fun EditTextFieldPreview() {
    EditTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "Placeholder",
        onValueChange = {}
    )
}
