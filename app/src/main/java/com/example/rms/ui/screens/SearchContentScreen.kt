package com.example.rms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rms.core.ui.components.ReceivableCard
import com.example.rms.core.ui.components.ReceivableStatus
import com.example.rms.core.ui.components.ReceivableUiModel
import com.example.rms.core.ui.components.SearchField

private val sampleReceivables = listOf(
    ReceivableUiModel("1", "João Silva", "Website Development", "R$ 2.500,00", "R$ 1.500,00", "15/09/2026", ReceivableStatus.OVERDUE),
    ReceivableUiModel("2", "Maria Santos", "Consultoria", "R$ 3.000,00", "R$ 3.000,00", "01/10/2026", ReceivableStatus.PENDING),
    ReceivableUiModel("3", "Pedro Oliveira", "Manutenção mensal", "R$ 1.200,00", "R$ 600,00", "10/10/2026", ReceivableStatus.PARTIAL),
    ReceivableUiModel("4", "Ana Costa", "Design de aplicativo", "R$ 4.800,00", "R$ 4.800,00", "20/10/2026", ReceivableStatus.PENDING)
)

@Composable
fun SearchContentScreen() {
    var query by remember { mutableStateOf("") }
    val normalizedQuery = query.trim().lowercase()
    val filteredReceivables = remember(query) {
        if (normalizedQuery.isEmpty()) sampleReceivables else sampleReceivables.filter {
            it.clientName.lowercase().contains(normalizedQuery) ||
                it.description.lowercase().contains(normalizedQuery)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            Text("Recebíveis", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Consulte por cliente ou descrição",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            SearchField(
                
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = { query = it },
                placeholder = "Buscar por cliente ou descrição"
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (filteredReceivables.isEmpty()) {
                item {
                    Text(
                        "Nenhum recebível encontrado para \"$query\".",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            } else {
                items(filteredReceivables, key = { it.id }) { receivable ->
                    ReceivableCard(receivable = receivable, onClick = {})
                }
            }
        }
    }
}

@Composable
fun SearchContentScreenPreview() {
    com.example.rms.ui.theme.TemplateAppTheme { SearchContentScreen() }
}
