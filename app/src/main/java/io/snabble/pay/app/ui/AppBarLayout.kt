package io.snabble.pay.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun AppBarLayout(
    title: String,
    navigator: DestinationsNavigator?,
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
                IconButton(onClick = { navigator?.navigateUp() }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onPrimary,
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            backgroundColor = MaterialTheme.colorScheme.background,
        )
        content()
    }
}
