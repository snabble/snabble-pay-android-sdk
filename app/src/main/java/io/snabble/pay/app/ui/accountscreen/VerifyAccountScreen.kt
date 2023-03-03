package io.snabble.pay.app.ui.accountscreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Destination
@Composable
fun VerifyAccountScreen(
    navigator: DestinationsNavigator?,
) {
    val context = LocalContext.current
    AppBarLayout(
        title = "Bankverbindung hinzufügen",
        navigator = navigator
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Verifizierung deines Bankkontos",
                    style = MaterialTheme.typography.headlineLarge,
                    letterSpacing = 1.5.sp
                )
                InfoText(
                    modifier = Modifier.padding(top = 32.dp),
                    string = "Für die Nutzung von Snabble Pay ist eine Bestätigung deines Bankkontos notwendig. Dazu musst du dich über unseren Dienstleister, die Tink AB, bei deiner Bank anmelden und dein Konto bestätigen. "
                )
                InfoText(
                    modifier = Modifier.padding(top = 32.dp),
                    string = "Du hast kein Konto bei einer deutschen Bank?"
                )
                HyperLinkText(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    string = "Dann klicke bitte hier für die Länderauswahl."
                ) {}
                InfoText(
                    modifier = Modifier.padding(top = 32.dp),
                    string = "Weitere Informationen zu unserer Verarbeitung deiner persönlichen Daten findest du in unseren "
                )
                HyperLinkText(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    string = "Datenschutzhinweis."
                ) {}
                Spacer(modifier = Modifier.weight(1f))
                ElevatedButton(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onClick = { start(context) }) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Konto verifizieren",
                    )

                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun VerifyAccountScreenPreview() {
    SnabblePayTheme {
        VerifyAccountScreen(null)
    }
}

fun start(context: Context) {
    val view = Intent()
    view.action = Intent.ACTION_VIEW
    view.data = android.net.Uri.parse("https://www.google.com/")
    startActivity(context, view, null)
}