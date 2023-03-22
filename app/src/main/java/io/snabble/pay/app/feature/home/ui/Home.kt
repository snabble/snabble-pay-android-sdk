package io.snabble.pay.app.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.snabble.pay.app.feature.home.ui.widget.SnabblePayHeader
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun Home(
    onFloatingActionButtonClick: () -> Unit,
    cardComposable: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            SnabblePayHeader(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(32.dp))
            cardComposable()
            Spacer(modifier = Modifier.weight(1f))
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
            onFloatingActionButtonClick = {}
        )
    }
}
