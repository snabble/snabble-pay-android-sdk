package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import io.snabble.pay.mandate.domain.model.Mandate.State
import io.snabble.pay.mandate.domain.model.Mandate.State.ACCEPTED
import io.snabble.pay.mandate.domain.model.Mandate.State.DECLINED
import io.snabble.pay.mandate.domain.model.Mandate.State.PENDING

@Composable
fun MandateState(
    modifier: Modifier = Modifier,
    mandateState: State,
    mandateNumber: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 60.dp)
            .then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            MandateStateIcon(mandateState = mandateState)
            Text(
                modifier = Modifier.padding(start = 6.dp),
                text = when (mandateState) {
                    ACCEPTED -> stringResource(id = R.string.mandate_accepted)
                    DECLINED -> stringResource(id = R.string.mandate_declined)
                    PENDING -> stringResource(id = R.string.mandate_pending)
                },
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 0.1.sp
            )
        }
        Text(
            modifier = Modifier.padding(start = 26.dp),
            text = mandateNumber,
            style = MaterialTheme.typography.labelSmall,
            color = Color.DarkGray.copy(0.6f)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AcceptedStatePreview() {
    MandateState(
        mandateState = ACCEPTED,
        mandateNumber = "TM-234563445"
    )
}

@Preview(
    showBackground = true
)
@Composable
fun PendingStatePreview() {
    MandateState(
        mandateState = PENDING,
        mandateNumber = "TM-234563445"
    )
}

@Preview(
    showBackground = true
)
@Composable
fun DeclinedStatePreview() {
    MandateState(
        mandateState = DECLINED,
        mandateNumber = "TM-234563445"
    )
}
