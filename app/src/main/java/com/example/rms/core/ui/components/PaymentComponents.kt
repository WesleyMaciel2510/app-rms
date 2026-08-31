package com.example.rms.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class PaymentMethod {
    PIX,
    CREDIT_CARD,
    DEBIT_CARD,
    BOLETO,
    CASH,
    TED,
    DIGITAL_WALLET
}

@Composable
fun PaymentMethodSelector(
    modifier: Modifier = Modifier,
    selectedMethod: PaymentMethod?,
    onMethodSelected: (PaymentMethod) -> Unit
) {
    val methods = listOf(
        PaymentMethod.PIX to Pair("PIX", Icons.Default.Schedule),
        PaymentMethod.CREDIT_CARD to Pair("Credit Card", Icons.Default.Cancel),
        PaymentMethod.DEBIT_CARD to Pair("Debit Card", Icons.Default.Cancel),
        PaymentMethod.BOLETO to Pair("Boleto", Icons.Default.Cancel),
        PaymentMethod.CASH to Pair("Cash", Icons.Default.Person),
        PaymentMethod.TED to Pair("TED", Icons.Default.History),
        PaymentMethod.DIGITAL_WALLET to Pair("Digital Wallet", Icons.Default.AccountCircle)
    )
    
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(methods) { (method, info) ->
            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { onMethodSelected(method) },
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedMethod == method) 
                        MaterialTheme.colorScheme.primaryContainer 
                    else 
                        MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = info.second,
                        contentDescription = info.first,
                        tint = if (selectedMethod == method) 
                            MaterialTheme.colorScheme.onPrimaryContainer 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = info.first,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedMethod == method) 
                            MaterialTheme.colorScheme.onPrimaryContainer 
                        else 
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}