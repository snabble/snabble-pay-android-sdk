package io.snabble.pay.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.google.zxing.client.android.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import io.snabble.pay.app.ui.homescreen.HomeScreen
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.core.dsl.snabblePay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnabblePayTheme {
                // A surface container using the 'background' color from the theme
                HomeScreen()
            }
        }

        val snabblePay = snabblePay(context = this) {
            if (BuildConfig.DEBUG) setBaseUrl("https://payment.snabble-testing.io")
            setOnNewAppCredentialsCallback { id: String, secret: String ->
                Log.d("xx", "ID: $id, Secret: $secret")
            }
            setAppCredentials(
                appId = "772c5363-bfc9-4ce5-9a56-c0076f67c022",
                appSecret = "InV3ecGpglu19ZLGMunPSQys3Kh6kygFJAe59xU1Xooy3faEFJQabMW6ih17ABT84z/Y2mGu72YquScaVaDw=="
            )
            setSnabblePayKey(
                "IO2wX69CsqZUQ3HshOnRkO4y5Gy/kRar6Fnvkp94piA2ivUun7TC7MjukrgUKlu7g8W8/enVsPDT7Kvq28ycw=="
            )
        }

        lifecycleScope.launch {
            if (intent.data == null && snabblePay.getAccounts().getOrNull()?.isEmpty() == true) {
                // https://console.tink.com/demobank
                val result = snabblePay.addNewAccount(
                    appUri = "snabble-pay://account/check",
                    city = "Berlin",
                    twoLetterIsoCountryCode = "DE"
                )
                if (result.isSuccess) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(result.getOrNull()?.validationLink ?: "https://google.com")
                        )
                    )
                }
            }
            Log.d("xx", "Accounts: ${snabblePay.getAccounts().getOrNull()}")
        }
    }
}
