package com.example.rms.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.rms.core.ui.components.PrimaryButton
import com.example.rms.core.ui.components.SearchField
import com.example.rms.core.ui.theme.EWalletSpacing

@Composable
fun RecoveryPasswordScreen(
    onNavigateUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    androidx.compose.material3.Scaffold(
        topBar = {
            com.example.rms.core.ui.components.EWalletTopBar(
                title = "Recover Password",
                onNavigateUp = onNavigateUp
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = EWalletSpacing.standard),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Icon(
                imageVector = Icons.Default.LockReset,
                contentDescription = "Recover Password",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.height(64.dp)
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.major))
            
            // Title
            Text(
                text = "Forgot your password?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Enter your email and we'll help you recover access.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.section))
            
            // Email field
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                imeAction = ImeAction.Done
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.section))
            
            // Error message
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            }
            
            // Success message
            if (isSuccess) {
                Text(
                    text = "If an account exists with this email, you will receive recovery instructions.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(EWalletSpacing.standard))
                
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Back to login",
                    onClick = onNavigateUp
                )
            } else {
                // Continue button
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Continue",
                    onClick = {
                        isLoading = true
                        errorMessage = null
                        
                        if (email.isEmpty()) {
                            errorMessage = "Please enter your email"
                            isLoading = false
                        } else {
                            // Simulate successful request
                            isLoading = false
                            isSuccess = true
                        }
                    },
                    isLoading = isLoading
                )
            }
        }
    }
}