package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R
import io.snabble.pay.app.feature.detailsaccount.ui.widget.DefaultButton
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.utils.decodeUrlUtf8
import io.snabble.pay.mandate.domain.model.Mandate
import io.snabble.pay.mandate.domain.model.MandateState.PENDING

@Composable
fun Mandate(
    modifier: Modifier,
    mandate: Mandate?,
    onMandateAccept: () -> Unit,
) {
    if (mandate?.state == PENDING) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxWidth(),
            ) {
                MandateBody(
                    modifier = Modifier
                        .fillMaxWidth(),
                    mandateText = mandate.htmlText.decodeUrlUtf8()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.mandate_accept)
            ) {
                onMandateAccept()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
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
            onMandateAccept = {}
        )
    }
}
