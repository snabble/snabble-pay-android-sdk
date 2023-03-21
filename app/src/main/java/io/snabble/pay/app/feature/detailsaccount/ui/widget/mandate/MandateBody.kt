package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.ui.widgets.DefaultButton

@Composable
fun MandateBody(
    mandateText: String?,
    isVisible: Boolean,
    onAccept: (Boolean) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()
        .verticalScroll(rememberScrollState())) {
        if (isVisible) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = mandateText ?: "",
                textAlign = TextAlign.Justify,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Mandat akzeptieren"
            ) {
                onAccept(true)
            }
            Spacer(modifier = Modifier.height(8.dp))
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Mandat verweigern"
            ) {
                onAccept(false)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
