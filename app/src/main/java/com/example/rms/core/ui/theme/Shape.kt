package com.example.rms.core.ui.theme

import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val EWalletShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    small = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
    large = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
)

// Custom shapes for specific components
object EWalletCustomShapes {
    val card = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
    val primaryButton = androidx.compose.foundation.shape.RoundedCornerShape(18.dp)
    val textField = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    val bottomSheet = androidx.compose.foundation.shape.RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 24.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    val statusChip = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
    val iconContainer = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    val floatingActionButton = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
}