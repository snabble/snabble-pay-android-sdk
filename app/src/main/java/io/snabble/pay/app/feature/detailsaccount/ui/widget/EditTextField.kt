package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditTextFieldCentered(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    onAction: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val focusedDivider = remember {
        mutableStateOf(false)
    }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Transparent,
        backgroundColor = Transparent,
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = modifier
                    .focusRequester(focusRequester)
                    .onFocusEvent {
                        focusedDivider.value = it.isFocused
                    },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent
                ),
                leadingIcon = {
                    Spacer(modifier = Modifier.size(52.dp))
                },
                trailingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSecondary,
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
                },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    letterSpacing = 0.1.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = if (focusedDivider.value) MaterialTheme.colorScheme.onSecondary else DividerDefaults.color
            )
        }
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
        onAction = {},
    )
}

@Composable
fun EditTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    onAction: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val focusedDivider = remember {
        mutableStateOf(false)
    }
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Transparent,
        backgroundColor = Transparent,
    )
    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = modifier
                    .focusRequester(focusRequester)
                    .onFocusEvent {
                        focusedDivider.value = it.isFocused
                    },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedIndicatorColor = Transparent,
                    disabledIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent
                ),
                trailingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSecondary,
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
                },
                maxLines = 1,
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    letterSpacing = 0.1.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            )
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = if (focusedDivider.value) MaterialTheme.colorScheme.onSecondary else DividerDefaults.color
            )
        }
    }
}
