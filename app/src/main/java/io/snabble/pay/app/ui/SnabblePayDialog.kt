package io.snabble.pay.app.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.snabble.pay.app.R
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun SnabblePayDialog(
    dialogTitle: String,
    dialogText: String,
    confirmButtonLabel: String,
    onConfirm: () -> Unit,
    cancelButtonLabel: String,
    onDismiss: () -> Unit,
) {
    SnabblePayTheme {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 28.dp)
            ) {
                Box(modifier = Modifier.padding(top = 16.dp, end = 16.dp)) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = false)
                            ) {
                                onDismiss()
                            },
                        painter = painterResource(id = R.drawable.ic_clear),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.padding(all = 24.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = dialogTitle,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface,
                        text = dialogText,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 32.dp)
                    ) {
                        CancelButton(label = cancelButtonLabel, onClick = onDismiss)
                        ConfirmButton(confirmButtonLabel, onConfirm)
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.ConfirmButton(confirmButtonLabel: String, onConfirmation: () -> Unit) {
    CompositionLocalProvider(LocalRippleTheme provides OkButtonRippleTheme) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f),
            colors = buttonColors(
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                disabledBackgroundColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f)
                    .compositeOver(MaterialTheme.colorScheme.surface),
                disabledContentColor = MaterialTheme.colorScheme.tertiary
                    .copy(alpha = ContentAlpha.disabled)
            ),
            shape = RoundedCornerShape(percent = 100),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
            onClick = { onConfirmation() }
        ) {
            Text(
                text = confirmButtonLabel,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun RowScope.CancelButton(label: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .weight(weight = 1f),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onSurface),
        colors = buttonColors(
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledBackgroundColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                .compositeOver(MaterialTheme.colorScheme.surface),
            disabledContentColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = ContentAlpha.disabled)
        ),
        shape = RoundedCornerShape(percent = 100),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

private object OkButtonRippleTheme : RippleTheme {

    @Composable override fun defaultColor() = MaterialTheme.colorScheme.onTertiary

    @Composable override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        MaterialTheme.colorScheme.onTertiary,
        lightTheme = !isSystemInDarkTheme()
    )
}
