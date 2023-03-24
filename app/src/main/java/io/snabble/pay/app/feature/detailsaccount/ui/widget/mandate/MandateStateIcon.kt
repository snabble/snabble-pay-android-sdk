package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R
import io.snabble.pay.mandate.domain.model.MandateState

@Composable
fun MandateStateIcon(
    modifier: Modifier = Modifier,
    mandateState: MandateState,
) {
    Surface(
        modifier = Modifier
            .defaultMinSize(
                minWidth = 33.dp,
                minHeight = 33.dp
            )
            .then(modifier),
        shape = CircleShape,
        color = colorResource(id = R.color.gray)
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            imageVector = when (mandateState) {
                MandateState.ACCEPTED -> Icons.Filled.Check
                MandateState.DECLINED -> Icons.Filled.Clear
                MandateState.PENDING -> Icons.Filled.QuestionMark
            },
            contentDescription = "",
            tint = Color.Black
        )
    }
}

@Preview
@Composable
fun AcceptedIconPreview() {
    MandateStateIcon(mandateState = MandateState.ACCEPTED)
}

@Preview
@Composable
fun PendingIconPreview() {
    MandateStateIcon(mandateState = MandateState.PENDING)
}

@Preview
@Composable
fun DeclinedIconPreview() {
    MandateStateIcon(mandateState = MandateState.DECLINED)
}
