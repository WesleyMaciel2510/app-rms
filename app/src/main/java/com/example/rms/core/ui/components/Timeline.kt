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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TransactionEvent(
    val type: TransactionEventType,
    val amount: String,
    val method: String? = null,
    val timestamp: String,
    val note: String? = null
)

enum class TransactionEventType {
    RECEIVABLE_CREATED,
    PAYMENT_RECEIVED,
    CANCELLATION,
    MANUAL_ADJUSTMENT
}

@Composable
fun TransactionTimeline(
    modifier: Modifier = Modifier,
    events: List<TransactionEvent>
) {
    Column(modifier = modifier) {
        events.forEachIndexed { index, event ->
            TransactionTimelineItem(event = event)
            
            if (index < events.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    modifier = Modifier.padding(start = 24.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun TransactionTimelineItem(event: TransactionEvent) {
    val icon = when (event.type) {
        TransactionEventType.RECEIVABLE_CREATED -> Icons.Default.Schedule
        TransactionEventType.PAYMENT_RECEIVED -> Icons.Default.CheckCircle
        TransactionEventType.CANCELLATION -> Icons.Default.Cancel
        TransactionEventType.MANUAL_ADJUSTMENT -> Icons.Default.History
    }
    
    val tint = when (event.type) {
        TransactionEventType.RECEIVABLE_CREATED -> MaterialTheme.colorScheme.primary
        TransactionEventType.PAYMENT_RECEIVED -> MaterialTheme.colorScheme.primary
        TransactionEventType.CANCELLATION -> MaterialTheme.colorScheme.error
        TransactionEventType.MANUAL_ADJUSTMENT -> MaterialTheme.colorScheme.tertiary
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.padding(top = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.type.name.replace("_", " "),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = event.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = event.amount,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            if (event.method != null) {
                Text(
                    text = event.method,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (event.note != null) {
                Text(
                    text = event.note,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}