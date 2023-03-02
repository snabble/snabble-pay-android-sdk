package io.snabble.pay.app.ui.accountcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import io.snabble.pay.app.domain.accountCard.AccountCardViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AccountCardPager(
    modifier: Modifier = Modifier,
    viewModel: AccountCardViewModel = AccountCardViewModel(),
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val sliderList = viewModel.accountCardList

    Column(
        modifier = Modifier
            .then(modifier),
    ) {
        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 24.dp),
            count = sliderList.size,
            itemSpacing = (-14).dp,
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
        ) { page ->
            AccountCard(
                accountCard = sliderList[page],
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
                    }
            )
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.onPrimary,
            inactiveColor = MaterialTheme.colorScheme.secondary,
        )

    }
}

@Preview
@Composable
fun AccountCardPagerPreview() {
    AccountCardPager(
        modifier = Modifier.background(Color.White)
    )
}
