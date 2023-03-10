package io.snabble.pay.app.feature.detailsaccount.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.R

@Composable
fun MandateGranted(
    modifier: Modifier,
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 60.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.defaultMinSize(
                    minWidth = 40.dp,
                    minHeight = 40.dp
                ),
                shape = CircleShape,
                color = colorResource(id = R.color.gray)
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.Filled.Check,
                    contentDescription = ""
                )
            }
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Sepa Mandat erteilt"
            )
        }
    }
}
