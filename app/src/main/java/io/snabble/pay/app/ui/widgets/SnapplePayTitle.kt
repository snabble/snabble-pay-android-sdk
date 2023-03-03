package io.snabble.pay.app.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.snabble.pay.app.R

@Composable
fun SnapplePayTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SNABBLE",
            style = MaterialTheme.typography.displaySmall,
            letterSpacing = 6.sp
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "PAY",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            letterSpacing = 6.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_logo),
            contentDescription = ""
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SnabblePayTitlePreview() {
    SnapplePayTitle()
}
