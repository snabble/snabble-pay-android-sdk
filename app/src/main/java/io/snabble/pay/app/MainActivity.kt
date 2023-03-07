package io.snabble.pay.app

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import io.snabble.pay.app.ui.screens.NavGraphs
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.core.SnabblePay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var snabblePay: SnabblePay
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnabblePayTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }

        lifecycleScope.launch {
            snabblePay.getAccounts()
            Log.d("xx", "onCreate: ${sharedPreferences.getString("appId", "")}")
        }

        lifecycleScope.launch {
            if (intent.data == null && snabblePay.getAccounts().getOrDefault(emptyList())
                    .isEmpty()
            ) {
                // https://console.tink.com/demobank
                val result = snabblePay.addNewAccount(
                    appUri = "snabble-pay://account/check",
                    city = "Berlin",
                    twoLetterIsoCountryCode = "DE"
                )
                Log.d("xx", "onCreate: $result")
                if (result.isSuccess) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(result.getOrNull()?.validationLink ?: "https://google.com")
                        )
                    )
                }
            }
        }
    }
}
