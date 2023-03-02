package io.snabble.pay.app.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.snabble.pay.app.R.drawable
import io.snabble.pay.app.ui.accountcard.AccountCardPager
import io.snabble.pay.app.ui.destinations.VerifyAccountScreenDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
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
                fontWeight = FontWeight.Bold,
            )
            AccountCardPager(modifier = Modifier
                .constrainAs(pager) {
                    top.linkTo(subtitle.bottom, margin = 32.dp)
//                    linkTo( top = subtitle.bottom, bottom = button.top, topMargin = 32.dp, bias = 0f)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
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
                    navigator.navigate(VerifyAccountScreenDestination)
                }) {
                Icon(
                    tint = MaterialTheme.colorScheme.onPrimary,
                    painter = painterResource(id = drawable.ic_snabble_add),
                    contentDescription = ""
                )
            }

        }
    }
}

@Composable
fun SnapplePayTitle(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SNABBLE",
            style = MaterialTheme.typography.displaySmall,
            letterSpacing = 6.sp
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "PAY",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            letterSpacing = 6.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = drawable.ic_snabble_logo),
            contentDescription = ""
        )

    }
}

@Preview
@Composable
fun SnabblePayTitlePreview() {
    SnapplePayTitle()
}

@Preview
@Composable
fun HomeScreenPreview() {
}
