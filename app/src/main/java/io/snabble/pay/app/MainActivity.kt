package io.snabble.pay.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import io.snabble.pay.SnabblePay
import io.snabble.pay.app.feature.NavGraphs
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.core.util.Success
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var snabblePay: SnabblePay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAndRecallDeeplink()

        setContent {
            SnabblePayTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }

    // TODO: Get rid of this as soon as the accountId is part of the deeplink
    private fun checkAndRecallDeeplink() {
        intent.data ?: return

        val uri = Uri.parse(intent.dataString)
        if (uri.host == "account" &&
            uri.path == "/check" &&
            !uri.queryParameterNames.contains("accountId")
        ) {
            lifecycleScope.launch {
                val result = snabblePay.getAccounts()
                if (result is Success){
                    val newAccountId = result.value.last().id
                    startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("$uri?accountId=$newAccountId")
                        }
                    )
                }
            }
        }
    }
}
