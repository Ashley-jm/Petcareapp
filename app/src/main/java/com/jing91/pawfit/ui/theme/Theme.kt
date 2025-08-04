package com.jing91.pawfit.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = LogoOrange,
    onPrimary = White,
    secondary = LogoLightPink,
    onSecondary = Black,
    background = LogoBackground,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

private val DarkColors = darkColorScheme(
    primary = LogoDark,
    onPrimary = White,
    secondary = LogoLightPink,
    onSecondary = Black,
    background = Color(0xFF1C1C1C),
    onBackground = White,
    surface = Color(0xFF2C2C2C),
    onSurface = White
)

@Composable
fun PetcareAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            @Suppress("DEPRECATION")
            window?.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window!!, view).isAppearanceLightStatusBars = !useDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
