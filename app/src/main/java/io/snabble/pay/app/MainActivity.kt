package io.snabble.pay.app

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import io.snabble.pay.app.ui.screens.NavGraphs
import io.snabble.pay.app.ui.screens.destinations.NewAccountScreenDestination
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
        val startRoute =
            if (intent.data != null) NewAccountScreenDestination else NavGraphs.root.startRoute
        setContent {

            SnabblePayTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    startRoute = startRoute
                )
            }
        }

        lifecycleScope.launch {
            snabblePay.getAccounts()
        }
    }
}
