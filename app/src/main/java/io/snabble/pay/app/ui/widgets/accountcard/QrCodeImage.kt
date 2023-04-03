package io.snabble.pay.app.ui.widgets.accountcard

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.snabble.pay.app.R
import io.snabble.pay.app.domain.account.utils.QrCodeGenerator

@Composable
fun QrCodeImage(
    modifier: Modifier = Modifier,
    qrCodeToken: String?,
    isMandatePending: Boolean,
) {
    if (isMandatePending) {
        Icon(
            modifier = modifier,
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_snabble_mandate_missing),
            contentDescription = "",
            tint = Color(android.graphics.Color.parseColor("#79747E")).copy(alpha = 0.7f)
        )
    } else {
        if (qrCodeToken != null) {
            Image(
                modifier = modifier,
                bitmap = QrCodeGenerator.generateQrCode(qrCodeToken)
                    .asImageBitmap(),
                contentDescription = ""
            )
        } else {
            val composition by rememberLottieComposition(
                LottieCompositionSpec
                    .Url("https://lottie.host/9c4b94ad-50f0-4646-acdb-5551c81ccba3/bfta8wOvr6.json")
            )

            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
            LottieAnimation(
                modifier = modifier,
                composition = composition,
                progress = { progress },
                clipToCompositionBounds = true
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun QrCodeImagePreview() {
    QrCodeImage(
        qrCodeToken = "https://www.google.com/",
        isMandatePending = true
    )
}
