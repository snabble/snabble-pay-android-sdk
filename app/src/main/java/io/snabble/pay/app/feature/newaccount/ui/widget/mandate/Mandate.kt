package io.snabble.pay.app.feature.newaccount.ui.widget.mandate

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.snabble.pay.app.R
import io.snabble.pay.app.feature.newaccount.ui.widget.DefaultButton
import io.snabble.pay.app.utils.decodeUrlUtf8
import io.snabble.pay.mandate.domain.model.Mandate

@Composable
fun Mandate(
    modifier: Modifier,
    mandate: Mandate?,
    onMandateAccept: () -> Unit,
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val fontColor = if (isSystemInDarkTheme()) "rgb(251,251,255)" else ""
    if (mandate?.state == Mandate.State.PENDING) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .clip(MaterialTheme.shapes.medium)
                    .border(1.dp, DividerDefaults.color, MaterialTheme.shapes.medium)
                    .fillMaxWidth()
            ) {
                AndroidView(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState()),
                    factory = {
                        WebView(it).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            setBackgroundColor(backgroundColor.toArgb())
                            webViewClient = WebViewClient()
                            loadData(
                                formatMandate(
                                    mandate.htmlText.decodeUrlUtf8(),
                                    fontColor
                                ),
                                "text/html; charset=UTF-8",
                                null
                            )
                        }
                    }
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

fun formatMandate(body: String, color: String) = """
            <html>
              <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
                <style type="text/css">
                body { padding: 8px 8px;}
                  * { font-family: Roboto; color: $color }
                </style>
              </head>
              <body>
                 $body
              </body>
            </html>
        """
