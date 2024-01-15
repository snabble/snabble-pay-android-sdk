package io.snabble.pay.app.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.domain.transactions.PurchasesModel
import io.snabble.pay.app.domain.transactions.PurchasesState
import io.snabble.pay.app.feature.home.ui.widget.SnabblePayHeader
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import java.time.ZonedDateTime

@Composable
fun Home(
    onFloatingActionButtonClick: () -> Unit,
    cardComposable: @Composable () -> Unit,
    purchasesModel: List<PurchasesModel>? = null,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            SnabblePayHeader(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(56.dp))
            cardComposable()
            Spacer(modifier = Modifier.weight(1f))
            if (purchasesModel != null) {
                PurchasesWidget(purchasesModel = purchasesModel)
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(End)
                    .padding(end = 32.dp, bottom = 32.dp),
                containerColor = Color.White,
                shape = CircleShape,
                onClick = { onFloatingActionButtonClick() }
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onPrimary,
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    SnabblePayTheme {
        Home(
            cardComposable = { Text(text = "Your Cards will bew here") },
            onFloatingActionButtonClick = {},
            purchasesModel = listOf(
                PurchasesModel(
                    ZonedDateTime.now(),
                    2,
                    "Mein Bankkonto",
                    PurchasesState.SUCCESS
                ),
                PurchasesModel(
                    ZonedDateTime.now(),
                    21231,
                    "Gemeinschaftskonto",
                    PurchasesState.FAILED
                )
            )
        )
    }
}

@Composable
fun PurchasesWidget(
    modifier: Modifier = Modifier,
    purchasesModel: List<PurchasesModel>,
) {
    Column(
        modifier = modifier.scrollable(
            state = rememberScrollState(),
            orientation = Orientation.Vertical
        )
    ) {

        purchasesModel.forEach {
            PurchaseLineItemWidget(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                purchasesModel = it
            )
            Divider(Modifier.fillMaxWidth())
        }

    }
}

@Composable
fun PurchaseLineItemWidget(
    modifier: Modifier = Modifier,
    purchasesModel: PurchasesModel,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(12.dp)
                .background(
                    color = if (purchasesModel.state == PurchasesState.SUCCESS) Color.Green else Color.Red
                )
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = "${purchasesModel.data.dayOfMonth}. ${purchasesModel.data.month}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(
            horizontalAlignment = End
        ) {
            Text(
                text = "- ${purchasesModel.amount.asPriceString()} €",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = purchasesModel.cardName,
                style = MaterialTheme.typography.labelSmall,
                color = Color.DarkGray.copy(alpha = 0.7f)
            )
        }

    }
}

private fun Int.asPriceString(): String {
    val amount = toString()
    return when {
        amount.length == 1 -> "0,0$amount"
        amount.length < 3 -> "0,$amount"
        amount.length < 6 -> "${
            amount.substring(
                0,
                amount.lastIndex - 1
            )
        },${amount.substring(amount.lastIndex - 2, amount.lastIndex)}"

        else -> "${amount.substring(0, amount.lastIndex - 4)}.${
            amount.substring(amount.lastIndex - 5, amount.lastIndex - 2)
        },${
            amount.substring(
                amount.lastIndex - 1, amount.lastIndex
            )
        }"
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PurchaseLineItemWidget(
        modifier = Modifier.fillMaxWidth(),
        purchasesModel = PurchasesModel(
            ZonedDateTime.now(),
            2,
            "Shush",
            PurchasesState.SUCCESS
        )
    )
}
