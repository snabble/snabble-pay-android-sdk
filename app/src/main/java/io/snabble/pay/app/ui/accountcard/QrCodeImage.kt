package io.snabble.pay.app.ui.accountcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import io.snabble.pay.app.domain.accountCard.utils.QrCodeGenerator

@Composable
fun QrCodeImage(
    modifier: Modifier = Modifier,
    qrCodeToken: String?,
) {
    if (qrCodeToken != null) {
        Image(
            modifier = modifier,
            bitmap = QrCodeGenerator.generateQrCode(qrCodeToken).asImageBitmap(),
            contentDescription = ""
        )
    } else {
        Spacer(
            modifier = modifier
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun QrCodeImagePreview() {
    QrCodeImage(
        qrCodeToken = "https://www.google.com/"
    )
}
