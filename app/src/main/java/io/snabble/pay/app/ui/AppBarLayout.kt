package io.snabble.pay.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@Composable
fun AppBarLayout(
    title: String,
    icon: ImageVector = Icons.Filled.ArrowBack,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        imageVector = icon,
                        contentDescription = "Back"
                    )
                }
            },
            backgroundColor = MaterialTheme.colorScheme.background
        )
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun AppBarLayoutPreview() {
    SnabblePayTheme {
        AppBarLayout(
            title = "DemoBar",
            onBackClick = {},
        ) {}
    }
}
