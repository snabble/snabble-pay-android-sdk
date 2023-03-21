package io.snabble.pay.app.feature.detailsaccount.ui.widget.mandate

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.snabble.pay.app.ui.widgets.DefaultButton

@Composable
fun MandateBody(
    mandateText: String,
    isVisible: Boolean,
    onAccept: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        if (isVisible) {
            AndroidView(
                modifier = Modifier.padding(horizontal = 16.dp),
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        loadData(mandateText, "text/html; charset=UTF-8", null)
                    }
                }, update = {
                    it.loadData(mandateText, "text/html; charset=UTF-8", null)
                })
            Spacer(modifier = Modifier.height(16.dp))
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
