package io.snabble.pay.app.feature.verifyaccount.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.R
import io.snabble.pay.app.data.viewModelStates.Error
import io.snabble.pay.app.data.viewModelStates.StartValidationFlow
import io.snabble.pay.app.feature.verifyaccount.VerifyAccountViewModel
import io.snabble.pay.app.feature.verifyaccount.ui.widget.HyperLinkText
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.DefaultButton
import io.snabble.pay.app.ui.widgets.InfoText
import io.snabble.pay.app.utils.browseUrl

@Destination
@Composable
fun VerifyAccountScreen(
    navigator: DestinationsNavigator?,
    verifyAccountViewModel: VerifyAccountViewModel = hiltViewModel(),
) {
    val uiState = verifyAccountViewModel.uiState.collectAsState()

    when (val it = uiState.value) {
        is StartValidationFlow -> LocalContext.current.browseUrl(it.validationLink)
        is Error -> Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_SHORT).show()
        else -> {}
    }

    AppBarLayout(
        title = "Bankverbindung hinzufügen",
        onBackClick = { navigator?.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.verify_bank),
                style = MaterialTheme.typography.headlineLarge,
                letterSpacing = 1.5.sp
            )
            InfoText(
                modifier = Modifier.padding(top = 32.dp),
                string = stringResource(id = R.string.verify_bank_description)
            )
            InfoText(
                modifier = Modifier.padding(top = 32.dp),
                string = stringResource(id = R.string.verify_account_exists)
            )
            HyperLinkText(
                modifier = Modifier
                    .padding(top = 4.dp),
                string = stringResource(id = R.string.verify_country_choice_link)
            ) {}
            InfoText(
                modifier = Modifier.padding(top = 32.dp),
                string = stringResource(id = R.string.verify_privacy_description)
            )
            HyperLinkText(
                modifier = Modifier
                    .padding(top = 4.dp),
                string = stringResource(id = R.string.verify_privacy_link)
            ) {}
            Spacer(modifier = Modifier.weight(1f))
            DefaultButton(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .height(40.dp),
                text = stringResource(id = R.string.verify_account)
            ) {
                verifyAccountViewModel.getValidationLink()
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
