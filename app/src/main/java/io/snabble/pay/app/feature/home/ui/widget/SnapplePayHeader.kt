package io.snabble.pay.app.feature.home.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.snabble.pay.app.R

@Composable
fun SnabblePayHeader(modifier: Modifier) {
    Column(modifier = modifier) {
        SnapplePayTitle(modifier = modifier.fillMaxWidth())
        Spacer(modifier = modifier.height(8.dp))
        SnabblePaySubTitle(modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun SnapplePayTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.home_snabble),
            style = MaterialTheme.typography.displaySmall,
            letterSpacing = 6.sp
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(id = R.string.home_pay),
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

@Composable
fun SnabblePaySubTitle(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.home_subtitle),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}


@Preview(
    showBackground = true
)
@Composable
fun SnabblePayTitlePreview() {
    SnapplePayTitle(modifier = Modifier.fillMaxWidth())
}


@Preview(
    showBackground = true
)
@Composable
fun SnabblePaySubTitlePreview() {
    SnabblePaySubTitle(modifier = Modifier.fillMaxWidth())
}

@Preview(
    showBackground = true
)
@Composable
fun SnabblePayHeaderPreview() {
    SnabblePayHeader(modifier = Modifier.fillMaxWidth())
}


