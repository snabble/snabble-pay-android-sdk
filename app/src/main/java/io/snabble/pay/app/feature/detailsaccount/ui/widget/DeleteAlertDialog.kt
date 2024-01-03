package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun DeleteAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    cancelButtonLabel: String,
    confirmButtonLabel: String,
) {
    SnabblePayTheme {
        AlertDialog(
            title = {
                Text(
                    text = dialogTitle,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = dialogText,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(
                        text = confirmButtonLabel,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = cancelButtonLabel,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }
}
