package io.snabble.pay.app.ui.accountcard

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.*
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

    Box(
        modifier = Modifier
            .then(modifier),
    ) {
        HorizontalPager(
            itemSpacing = (-16).dp,
            contentPadding = PaddingValues(horizontal = 32.dp),
            count = sliderList.size,
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

    }
}

@Preview
@Composable
fun preiv() {
    AccountCardPager()
}