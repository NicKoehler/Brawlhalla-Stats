package com.nickoehler.brawlhalla.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.nickoehler.brawlhalla.core.domain.model.Theme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BrawlhallaTheme(
    theme: Theme = Theme.System,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val view = LocalView.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            when (theme) {
                Theme.System -> if (darkTheme)
                    dynamicDarkColorScheme(context)
                else
                    dynamicLightColorScheme(context)

                Theme.Light -> dynamicLightColorScheme(context)
                Theme.Dark -> dynamicDarkColorScheme(context)
            }
        }

        else -> {
            when (theme) {
                Theme.System -> if (darkTheme)
                    DarkColorScheme
                else
                    LightColorScheme

                Theme.Light -> LightColorScheme
                Theme.Dark -> DarkColorScheme
            }
        }
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // window.navigationBarColor = colorScheme.surfaceContainerHighest.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                when (theme) {
                    Theme.System -> !darkTheme
                    Theme.Light -> true
                    Theme.Dark -> false
                }

            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                when (theme) {
                    Theme.System -> !darkTheme
                    Theme.Light -> true
                    Theme.Dark -> false
                }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        motionScheme = MotionScheme.expressive(),
        content = content
    )
}