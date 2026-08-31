package com.example.rms.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rms.core.ui.components.EWalletTopBar
import com.example.rms.core.ui.components.EmptyState
import com.example.rms.core.ui.components.ErrorState
import com.example.rms.core.ui.components.FinancialBalanceCard
import com.example.rms.core.ui.components.KpiCard
import com.example.rms.core.ui.components.LoadingSkeleton
import com.example.rms.core.ui.components.PrimaryButton
import com.example.rms.core.ui.components.ReceivableCard
import com.example.rms.core.ui.components.ReceivableStatus
import com.example.rms.core.ui.components.ReceivableUiModel
import com.example.rms.core.ui.components.SecondaryButton
import com.example.rms.core.ui.theme.EWalletSpacing

@Composable
fun HomeScreen(
    onNavigateToScan: () -> Unit,
    onNavigateToStatistics: () -> Unit,
    onNavigateToSendMoney: () -> Unit,
    onReceivableClick: (String) -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            EWalletTopBar(
                title = "Home",
actions = {
                androidx.compose.material3.IconButton(onClick = onLogout) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Greeting
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EWalletSpacing.standard),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Good morning",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Welcome back!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(EWalletSpacing.standard))
            
            // Hero balance card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total Receivables",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                    Text(
                        text = "R$ 21.350,54",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Pending: R$ 4.500,00",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                        Text(
                            text = "Overdue: R$ 1.200,00",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.9f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(EWalletSpacing.standard))
            
            // KPI Cards
            Text(
                text = "Financial Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = EWalletSpacing.standard, bottom = EWalletSpacing.tight)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EWalletSpacing.standard),
                horizontalArrangement = Arrangement.spacedBy(EWalletSpacing.tight)
            ) {
                KpiCard(
                    modifier = Modifier.weight(1f),
                    title = "Received",
                    value = "R$ 15.650",
                    tintColor = MaterialTheme.colorScheme.primary
                )
                KpiCard(
                    modifier = Modifier.weight(1f),
                    title = "Pending",
                    value = "R$ 4.500",
                    tintColor = MaterialTheme.colorScheme.tertiary
                )
            }
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EWalletSpacing.standard),
                horizontalArrangement = Arrangement.spacedBy(EWalletSpacing.tight)
            ) {
                KpiCard(
                    modifier = Modifier.weight(1f),
                    title = "Overdue",
                    value = "R$ 1.200",
                    tintColor = MaterialTheme.colorScheme.error
                )
                KpiCard(
                    modifier = Modifier.weight(1f),
                    title = "Collection Rate",
                    value = "85.5%",
                    tintColor = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(EWalletSpacing.section))
            
            // Quick Actions
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = EWalletSpacing.standard, bottom = EWalletSpacing.tight)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EWalletSpacing.standard),
                horizontalArrangement = Arrangement.spacedBy(EWalletSpacing.tight)
            ) {
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    text = "Scan",
                    onClick = onNavigateToScan
                )
                SecondaryButton(
                    modifier = Modifier.weight(1f),
                    text = "Register Payment",
                    onClick = {}
                )
            }
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EWalletSpacing.standard),
                horizontalArrangement = Arrangement.spacedBy(EWalletSpacing.tight)
            ) {
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    text = "Receivables",
                    onClick = {}
                )
                SecondaryButton(
                    modifier = Modifier.weight(1f),
                    text = "Statistics",
                    onClick = onNavigateToStatistics
                )
            }
            
            Spacer(modifier = Modifier.height(EWalletSpacing.section))
            
            // Attention Required
            Text(
                text = "Attention Required",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = EWalletSpacing.standard, bottom = EWalletSpacing.tight)
            )
            
            ReceivableCard(
                modifier = Modifier.padding(horizontal = EWalletSpacing.standard),
                receivable = ReceivableUiModel(
                    id = "1",
                    clientName = "Joao Silva",
                    description = "Website Development",
                    originalAmount = "R$ 2.500,00",
                    remainingBalance = "R$ 1.500,00",
                    dueDate = "15/09/2026",
                    status = ReceivableStatus.OVERDUE
                ),
                onClick = { onReceivableClick("1") }
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            ReceivableCard(
                modifier = Modifier.padding(horizontal = EWalletSpacing.standard),
                receivable = ReceivableUiModel(
                    id = "2",
                    clientName = "Maria Santos",
                    description = "Consultoria",
                    originalAmount = "R$ 3.000,00",
                    remainingBalance = "R$ 3.000,00",
                    dueDate = "01/10/2026",
                    status = ReceivableStatus.PENDING
                ),
                onClick = { onReceivableClick("2") }
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.section))
            
            // Recent Activity
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = EWalletSpacing.standard, bottom = EWalletSpacing.tight)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = EWalletSpacing.standard),
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "No recent activity",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}