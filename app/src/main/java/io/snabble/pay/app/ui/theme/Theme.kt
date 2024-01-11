package io.snabble.pay.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val darkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = Color(0x7f0077BB),
    background = Color(0xFF1D1A22),
    onPrimary = Color(0xFF0077BB),
    onSecondary = Color(0xFF4BB4EF),
    onSurface = Color(0xFFEEF2FA)
)

private val lightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color(0x7f0077BB),
    background = Color(0xFFFBFBFF),
    onPrimary = Color(0xFF0077BB),
    onSecondary = Color(0xFF0077BB),
    onSurface = Color.Black
)

@Composable
fun SnabblePayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = colorScheme.background)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
