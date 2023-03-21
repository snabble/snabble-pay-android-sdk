package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateState.ACCEPTED
import io.snabble.pay.mandate.domain.model.MandateState.PENDING

@Composable
fun Mandate(
    modifier: Modifier,
    mandate: Mandate?,
    onAccept: (Boolean) -> Unit,
) {

    val showMandate = remember {
        mutableStateOf(false)
    }

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        MandateState(
            modifier = Modifier
                .clickable {
                    if (showMandate.value) {
                        showMandate.value = false
                    } else {
                        showMandate.value = mandate?.state == PENDING
                    }
                },
            mandateState = mandate?.state ?: PENDING
        )
        MandateBody(
            mandateText = mandate?.htmlText,
            isVisible = showMandate.value,
            onAccept = { onAccept(it) },
        )
    }
}

@Preview
@Composable
fun MandateGrantedPreview() {
    SnabblePayTheme {
        Mandate(
            modifier = Modifier.fillMaxWidth(),
            mandate = Mandate(
                id = "1",
                htmlText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam " +
                    "nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam " +
                    "voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd " +
                    "gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum " +
                    "dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt " +
                    "ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam" +
                    " et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus" +
                    " est Lorem ipsum dolor sit amet.",
                state = PENDING
            ),
            onAccept = {}
        )
    }
}
