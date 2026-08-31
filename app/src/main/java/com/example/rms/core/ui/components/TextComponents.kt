package com.example.rms.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.rms.core.formatting.Money
import com.example.rms.core.formatting.MoneyFormatter

@Composable
fun MoneyText(
    modifier: Modifier = Modifier,
    money: Money
) {
    Text(
        text = MoneyFormatter.format(money),
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun MoneyTextCompact(
    modifier: Modifier = Modifier,
    amount: String
) {
    Text(
        text = amount,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}