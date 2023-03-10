package io.snabble.pay.app.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String = "",
    onValueChange: (String) -> Unit,
    onAction: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier.focusRequester(focusRequester),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colorScheme.background,
            textColor = MaterialTheme.colorScheme.onSurface,
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onAction()
                focusManager.clearFocus()
            }
        ),
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
        onValueChange = {},
        onAction = {}
    )
}
