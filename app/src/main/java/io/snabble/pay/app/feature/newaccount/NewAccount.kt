package io.snabble.pay.app.feature.newaccount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.R
import io.snabble.pay.app.domain.account.AccountCard
import io.snabble.pay.app.feature.newaccount.ui.widget.AccountInformation
import io.snabble.pay.app.feature.newaccount.ui.widget.EditTextField
import io.snabble.pay.app.feature.newaccount.ui.widget.mandate.Mandate
import io.snabble.pay.app.ui.AppBarLayout
import io.snabble.pay.mandate.domain.model.Mandate

@Composable
fun NewAccount(
    navigator: DestinationsNavigator?,
    mandate: Mandate?,
    accountCard: AccountCard,
    onLabelChange: (label: String, colors: List<String>) -> Unit,
    onMandateAccept: () -> Unit,
) {
    val cardName = rememberSaveable(inputs = arrayOf(accountCard.name)) {
        mutableStateOf(accountCard.name)
    }

    AppBarLayout(
        title = stringResource(id = R.string.new_account_app_bar),
        onBackClick = { navigator?.navigateUp() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = cardName.value,
                onValueChange = { cardName.value = it },
                onAction = { onLabelChange(cardName.value, accountCard.cardBackgroundColor) }
            )
            AccountInformation(
                holderName = accountCard.holderName,
                iban = accountCard.iban,
                bank = accountCard.bank
            )
            Spacer(modifier = Modifier.height(16.dp))
            Mandate(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                mandate = mandate,
                onMandateAccept = onMandateAccept
            )
        }
    }
}
