package io.snabble.pay.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R

@Composable
fun AlertWidget(
    reason: String,
    message: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = Color(0xFFEEF2FA),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    id = R.string.error_reason, reason
                )
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.error_message, message)
            )
        },
        onDismissRequest = {},
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .widthIn(100.dp),
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = ("OK"),
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
    )
}

@Preview
@Composable
fun AlertPreview() {
    AlertWidget(
        reason = "Laden der Daten",
        message = "Falsche ID",
        onDismiss = {}
    )
}
