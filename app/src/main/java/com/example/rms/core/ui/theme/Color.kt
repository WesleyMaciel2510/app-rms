package com.example.rms.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

// E-Wallet Purple Visual Identity - Design Tokens
private val PrimaryPurple = Color(0xFF5B00D6)
private val PrimaryPurpleDark = Color(0xFF37007D)
private val PrimaryPurpleLight = Color(0xFF7C2DFF)

private val Background = Color(0xFFF7F7FC)
private val Surface = Color(0xFFFFFFFF)
private val SurfaceSecondary = Color(0xFFF0EFF7)

private val TextPrimary = Color(0xFF171321)
private val TextSecondary = Color(0xFF6F6A7A)
private val TextDisabled = Color(0xFFA6A2AE)

private val Success = Color(0xFF22C55E)
private val Warning = Color(0xFFF59E0B)
private val Error = Color(0xFFEF4444)
private val Info = Color(0xFF3B82F6)

private val DarkBackground = Color(0xFF141C1F)
private val DarkSurface = Color(0xFF1E2428)
private val DarkSurfaceSecondary = Color(0xFF2A3138)
private val DarkTextPrimary = Color(0xFFE2E2E5)
private val DarkTextSecondary = Color(0xFFC4C6C9)
private val DarkTextDisabled = Color(0xFF8E9093)

val EWalletLightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.White,
    primaryContainer = PrimaryPurpleLight.copy(alpha = 0.15f),
    onPrimaryContainer = PrimaryPurpleDark,
    secondary = PrimaryPurpleLight,
    onSecondary = Color.White,
    secondaryContainer = SurfaceSecondary,
    onSecondaryContainer = TextPrimary,
    tertiary = Info,
    onTertiary = Color.White,
    tertiaryContainer = Info.copy(alpha = 0.15f),
    onTertiaryContainer = Info,
    error = Error,
    onError = Color.White,
    errorContainer = Error.copy(alpha = 0.15f),
    onErrorContainer = Error,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceContainerHighest = SurfaceSecondary,
    onSurfaceVariant = TextSecondary,
    outline = TextDisabled,
    outlineVariant = TextDisabled.copy(alpha = 0.5f),
    background = Background,
    onBackground = TextPrimary,
    surfaceContainer = SurfaceSecondary,
    surfaceContainerLow = Surface,
    surfaceContainerLowest = Color.White,
    surfaceContainerHigh = SurfaceSecondary.copy(alpha = 0.8f),
    inverseSurface = DarkSurface,
    inverseOnSurface = DarkTextPrimary,
    inversePrimary = PrimaryPurpleLight,
    scrim = Color.Black,
    surfaceTint = PrimaryPurple,
)

val EWalletDarkColorScheme = darkColorScheme(
    primary = PrimaryPurpleLight,
    onPrimary = PrimaryPurpleDark,
    primaryContainer = PrimaryPurpleDark.copy(alpha = 0.3f),
    onPrimaryContainer = PrimaryPurpleLight,
    secondary = PrimaryPurpleLight,
    onSecondary = PrimaryPurpleDark,
    secondaryContainer = DarkSurfaceSecondary,
    onSecondaryContainer = DarkTextPrimary,
    tertiary = Info,
    onTertiary = Color.Black,
    tertiaryContainer = Info.copy(alpha = 0.2f),
    onTertiaryContainer = Info,
    error = Error,
    onError = Color.Black,
    errorContainer = Error.copy(alpha = 0.2f),
    onErrorContainer = Error,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceContainerHighest = DarkSurfaceSecondary,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkTextDisabled,
    outlineVariant = DarkTextDisabled.copy(alpha = 0.5f),
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surfaceContainer = DarkSurfaceSecondary,
    surfaceContainerLow = DarkSurface,
    surfaceContainerLowest = DarkBackground,
    surfaceContainerHigh = DarkSurfaceSecondary.copy(alpha = 0.8f),
    inverseSurface = Surface,
    inverseOnSurface = TextPrimary,
    inversePrimary = PrimaryPurple,
    scrim = Color.Black,
    surfaceTint = PrimaryPurpleLight,
)

object EWalletFinancialColors {
    val success: Color get() = Success
    val warning: Color get() = Warning
    val error: Color get() = Error
    val info: Color get() = Info
    val darkSuccess: Color get() = Success
    val darkWarning: Color get() = Warning
    val darkError: Color get() = Error
    val darkInfo: Color get() = Info
}