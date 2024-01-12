package io.snabble.pay.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import io.snabble.pay.app.ui.theme.SnabblePayTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarLayout(
    title: String,
    onBackClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    letterSpacing = 0.1.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSurface,
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
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
            onBackClick = {}
        ) {}
    }
}
