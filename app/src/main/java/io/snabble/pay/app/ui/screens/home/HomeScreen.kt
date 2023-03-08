package io.snabble.pay.app.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.ui.screens.destinations.DetailsAccountScreenDestination
import io.snabble.pay.app.ui.screens.destinations.VerifyAccountScreenDestination
import io.snabble.pay.app.ui.theme.SnabblePayTheme
import io.snabble.pay.app.ui.widgets.SnapplePayTitle
import io.snabble.pay.app.ui.widgets.accountcard.AccountCardPager

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator?,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState = homeViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (title, subtitle, pager, button) = createRefs()

            SnapplePayTitle(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 100.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(subtitle) {
                        top.linkTo(title.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = "The Future of Mobile Payment",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            when(val it = uiState.value){
                Loading -> {}
                is ShowCards -> {
                    AccountCardPager(
                        modifier = Modifier
                            .constrainAs(pager) {
                                top.linkTo(subtitle.bottom, margin = 32.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        accountList = it.list,
                        onCurrentPage = { homeViewModel.getSessionToken(it) }
                    ) { navigator?.navigate(DetailsAccountScreenDestination(it)) }
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .constrainAs(button) {
                        linkTo(
                            top = pager.bottom,
                            bottom = parent.bottom,
                            bottomMargin = 32.dp,
                            bias = 1f
                        )
                        end.linkTo(parent.end, margin = 32.dp)
                    },
                containerColor = Color.White,
                shape = CircleShape,
                onClick = {
                    navigator?.navigate(VerifyAccountScreenDestination)
                }
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
        HomeScreen(
            navigator = null,
            homeViewModel = hiltViewModel()
        )
    }
}