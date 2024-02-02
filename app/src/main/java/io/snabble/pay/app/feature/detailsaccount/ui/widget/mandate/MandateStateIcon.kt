package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.Mandate.State.ACCEPTED
import io.snabble.pay.mandate.domain.model.Mandate.State.DECLINED
import io.snabble.pay.mandate.domain.model.Mandate.State.PENDING

@Composable
fun MandateStateIcon(
    modifier: Modifier = Modifier,
    mandateState: Mandate.State,
) {
    Surface(
        modifier = Modifier
            .defaultMinSize(
                minWidth = 24.dp,
                minHeight = 24.dp
            )
            .then(modifier),
        shape = CircleShape,
        color = colorResource(id = R.color.gray),
        border = BorderStroke(1.5.dp, Color.Black)
    ) {
        Icon(
            modifier = Modifier
                .padding(4.dp)
                .size(10.dp),
            imageVector = when (mandateState) {
                ACCEPTED -> Icons.Filled.Check
                DECLINED -> Icons.Filled.Clear
                PENDING -> ImageVector.vectorResource(id = R.drawable.ic_snabble_mandate_missing)
            },
            contentDescription = "",
            tint = Color.Black
        )
    }
}

@Preview
@Composable
fun AcceptedIconPreview() {
    MandateStateIcon(mandateState = ACCEPTED)
}

@Preview
@Composable
fun PendingIconPreview() {
    MandateStateIcon(mandateState = PENDING)
}

@Preview
@Composable
fun DeclinedIconPreview() {
    MandateStateIcon(mandateState = DECLINED)
}
