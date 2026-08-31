package com.example.rms.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ReceivableStatus {
    PENDING, PARTIAL, PAID, OVERDUE, CANCELLED
}

@Composable
fun ReceivableStatusChip(
    modifier: Modifier = Modifier,
    status: ReceivableStatus
) {
    val icon = when (status) {
        ReceivableStatus.PENDING -> Icons.Default.Warning
        ReceivableStatus.PARTIAL -> Icons.Default.Close
        ReceivableStatus.PAID -> Icons.Default.CheckCircle
        ReceivableStatus.OVERDUE -> Icons.Default.Warning
        ReceivableStatus.CANCELLED -> Icons.Default.Close
    }
    
    val label = when (status) {
        ReceivableStatus.PENDING -> "PENDING"
        ReceivableStatus.PARTIAL -> "PARTIAL"
        ReceivableStatus.PAID -> "PAID"
        ReceivableStatus.OVERDUE -> "OVERDUE"
        ReceivableStatus.CANCELLED -> "CANCELLED"
    }
    
    val tint = when (status) {
        ReceivableStatus.PENDING -> MaterialTheme.colorScheme.primary
        ReceivableStatus.PARTIAL -> MaterialTheme.colorScheme.tertiary
        ReceivableStatus.PAID -> MaterialTheme.colorScheme.primary // Using primary as success proxy
        ReceivableStatus.OVERDUE -> MaterialTheme.colorScheme.error
        ReceivableStatus.CANCELLED -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "$label status",
            tint = tint,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = tint
        )
    }
}