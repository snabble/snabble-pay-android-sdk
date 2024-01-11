package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R

@Composable
fun DeleteButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(32.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.DeleteOutline,
            contentDescription = "",
            tint = Color.Black
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(id = R.string.details_delete_account),
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black
        )
    }
}
