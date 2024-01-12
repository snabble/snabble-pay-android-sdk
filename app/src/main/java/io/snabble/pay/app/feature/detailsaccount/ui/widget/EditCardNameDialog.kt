package io.snabble.pay.app.feature.detailsaccount.ui.widget

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun EditCardNameDialog(
    cardName: String,
    onDismissRequest: () -> Unit,
    onSaveNameRequest: (String) -> Unit,
) {
    val editTextValue = rememberSaveable(inputs = arrayOf(cardName)) {
        mutableStateOf(cardName)
    }
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Titel bearbeiten", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(16.dp))
                EditTextFieldCentered(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = editTextValue.value,
                    onValueChange = { editTextValue.value = it },
                    onAction = {
                        onSaveNameRequest(editTextValue.value)
                    }
                )
                Spacer(modifier = Modifier.size(16.dp))
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(32.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent
                    ),
                    onClick = {
                        onSaveNameRequest(editTextValue.value)
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp),
                        text = "Speichern",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditCardNameDialogPreview() {
    SnabblePayTheme {
        EditCardNameDialog(cardName = "Peter Pan", onDismissRequest = {}, onSaveNameRequest = {})
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun EditCardNameDialogPreviewDark() {
    SnabblePayTheme {
        EditCardNameDialog(cardName = "Peter Pan", onDismissRequest = {}, onSaveNameRequest = {})
    }
}
