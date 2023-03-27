package io.snabble.pay.app.ui.widgets.accountcard

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import io.snabble.pay.app.R
import io.snabble.pay.app.domain.account.utils.QrCodeGenerator

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
        Icon(
            modifier = modifier,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_qrcode),
            contentDescription = ""
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
