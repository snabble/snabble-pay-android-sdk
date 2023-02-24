package io.snabble.pay.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.core.BuildConfig
import io.snabble.pay.core.dsl.snabblePay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnabblePayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        val snabblePay = snabblePay(context = this) {
            if (BuildConfig.DEBUG) setBaseUrl("https://payment.snabble-testing.io")
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnabblePayTheme {
        Greeting("Android")
    }
}
