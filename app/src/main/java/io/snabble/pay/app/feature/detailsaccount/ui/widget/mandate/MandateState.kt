package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.snabble.pay.app.R
import io.snabble.pay.mandate.domain.model.MandateState

@Composable
fun MandateState(
    modifier: Modifier = Modifier,
    mandateState: MandateState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 60.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MandateStateIcon(mandateState = mandateState)
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = when (mandateState) {
                MandateState.ACCEPTED -> stringResource(id = R.string.mandate_accepted)
                MandateState.DECLINED -> stringResource(id = R.string.mandate_declined)
                MandateState.PENDING -> stringResource(id = R.string.mandate_pending)
            },
            color = Color.Black,
            style = MaterialTheme.typography.labelLarge,
            letterSpacing = 0.1.sp
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AcceptedStatePreview() {
    MandateState(mandateState = MandateState.ACCEPTED)
}

@Preview(
    showBackground = true
)
@Composable
fun PendingStatePreview() {
    MandateState(mandateState = MandateState.PENDING)
}

@Preview(
    showBackground = true
)
@Composable
fun DeclinedStatePreview() {
    MandateState(mandateState = MandateState.DECLINED)
}
