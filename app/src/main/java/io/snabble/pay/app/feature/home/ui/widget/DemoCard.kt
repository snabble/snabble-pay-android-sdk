package io.snabble.pay.app.feature.home.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.snabble.pay.app.domain.account.utils.GradiantGenerator
import io.snabble.pay.app.ui.widgets.accountcard.toColorList

@Composable
fun DemoCard(modifier: Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .then(modifier),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = GradiantGenerator
                            .createGradiantColorList()
                            .toColorList()
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your Card could be here!",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Start

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Click on the add Button below and get started!",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Start

            )
        }
    }
}

@Preview
@Composable
fun DemoCardPreview() {
    DemoCard(modifier = Modifier.padding(horizontal = 16.dp))
}