package io.snabble.pay.app.ui.accountcard

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrCodeGenerator {

    fun generateQrCodeFrom(token: String): Bitmap {
        val hintMap: Map<EncodeHintType, Any> = mapOf(
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L,
            EncodeHintType.MARGIN to 0
        )
//        val bitMapMatrix: BitMatrix = MultiFormatWriter().encode(
//            token,
//            BarcodeFormat.QR_CODE, 240, 240, hintMap
//        )
        val qrCodeWriter = QRCodeWriter()
        val bitMapMatrix = qrCodeWriter.encode(token, BarcodeFormat.QR_CODE, 240, 240, hintMap)

        val width = bitMapMatrix.width
        val height = bitMapMatrix.height

        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                pixels[y * width + x] =
                    if (bitMapMatrix.get(x, y)) Color.BLACK else Color.TRANSPARENT
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    fun generateQrCodeFrom2(token: String): Bitmap {
        return BarcodeEncoder().encodeBitmap(token, BarcodeFormat.QR_CODE, 240, 240)
    }

    companion object {

        fun generateQrCode(token: String) =
            QrCodeGenerator().generateQrCodeFrom(token)
    }
}