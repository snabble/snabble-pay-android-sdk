package io.snabble.pay.app.ui.widgets.accountcard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import io.snabble.pay.app.domain.accountCard.AccountCardModel
import io.snabble.pay.app.domain.accountCard.utils.GradiantGenerator
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AccountCardPager(
    modifier: Modifier = Modifier,
    accountList: List<AccountCardModel>,
    onCurrentPage: (String) -> Unit,
    onClick: (AccountCardModel) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page >= 0) {
                onCurrentPage(accountList[page].accountId)
            }
        }
    }

    Column(
        modifier = Modifier
            .then(modifier)
    ) {
        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 24.dp),
            count = accountList.size,
            itemSpacing = (-14).dp,
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            AccountCard(
                accountCard = accountList[page],
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                onClick = { onClick(it) },
                qrCodeString = if (page == pagerState.currentPage) accountList[page].qrCodeToken else null
            )
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.onPrimary,
            inactiveColor = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun AccountCardPagerPreview() {
    SnabblePayTheme {
        AccountCardPager(
            modifier = Modifier.padding(top = 16.dp),
            accountList = previewList,
            onClick = {},
            onCurrentPage = {},
        )
    }
}

private val previewList = listOf(
    AccountCardModel(
        cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
        qrCodeToken = "test",
        holderName = "Petra MusterMann",
        accountId = "1",
        iban = "DE91 1000 0000 0123 4567 89",
        bank = "Deutsche Bank"
    ),
    AccountCardModel(
        cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
        qrCodeToken = "test",
        holderName = "Muster Mann",
        accountId = "2",
        iban = "DE 1234 1234 1234 1234",
        bank = "Deutsche Bank"
    ),
    AccountCardModel(
        cardBackgroundColor = GradiantGenerator().createGradiantBackground(),
        qrCodeToken = "test",
        holderName = "Muster Mann",
        accountId = "3",
        iban = "DE 1234 1234 1234 1234",
        bank = "Deutsche Bank"
    )
)
