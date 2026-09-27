package com.example.rms.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeContentScreen(onNavigateToSearch: () -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.primary)
                Text("Visão geral", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("Acompanhe seus recebíveis", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onNavigateToSearch,
                    modifier = Modifier.weight(1f).heightIn(min = 76.dp),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text("Novo\nRecebível", textAlign = TextAlign.Center, modifier = Modifier.padding(start = 8.dp))
                }
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f).heightIn(min = 76.dp),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text("Registrar\nPagamento", textAlign = TextAlign.Center)
                }
            }
        }
        item {
            Text("Resumo financeiro", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        }
        item {
            Text("Use Novo Recebível para consultar e localizar recebíveis na tela Search.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun HomeContentScreenPreview() {
    com.example.rms.ui.theme.TemplateAppTheme { HomeContentScreen() }
}
