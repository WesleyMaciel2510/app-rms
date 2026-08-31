package com.example.rms.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSkeleton(
    modifier: Modifier = Modifier,
    width: androidx.compose.ui.unit.Dp = 200.dp,
    height: androidx.compose.ui.unit.Dp = 24.dp,
    shape: androidx.compose.foundation.shape.CornerBasedShape = RoundedCornerShape(8.dp)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape)
            .background(Color.LightGray.copy(alpha = 0.5f))
    )
}

@Composable
fun SkeletonLine(
    modifier: Modifier = Modifier
) {
    LoadingSkeleton(
        modifier = modifier.fillMaxWidth(),
        height = 16.dp
    )
}

@Composable
fun SkeletonCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray.copy(alpha = 0.5f))
    )
}