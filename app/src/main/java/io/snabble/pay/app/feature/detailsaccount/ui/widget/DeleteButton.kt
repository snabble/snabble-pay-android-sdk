package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R

@Composable
fun DeleteButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = colorResource(id = R.color.gray),
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_delete),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(id = R.string.details_delete_account)
        )
    }
}
