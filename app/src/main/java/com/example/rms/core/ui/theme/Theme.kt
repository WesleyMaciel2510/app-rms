package com.example.rms.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun EWalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) EWalletDarkColorScheme else EWalletLightColorScheme

    val systemUiController = rememberSystemUiController()
    val surfaceColor = colorScheme.surface

    androidx.compose.runtime.SideEffect {
        systemUiController.setStatusBarColor(
            color = surfaceColor.copy(alpha = 0f),
            darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = surfaceColor,
            darkIcons = !darkTheme
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = EWalletTypography,
        shapes = EWalletShapes,
        content = content
    )
}