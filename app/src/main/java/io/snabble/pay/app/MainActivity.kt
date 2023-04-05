package io.snabble.pay.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import io.snabble.pay.SnabblePay
import io.snabble.pay.app.feature.NavGraphs
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var snabblePay: SnabblePay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnabblePayTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
