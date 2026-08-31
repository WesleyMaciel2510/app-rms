package com.example.rms.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rms.core.ui.components.PasswordField
import com.example.rms.core.ui.components.PrimaryButton
import com.example.rms.core.ui.components.SearchField
import com.example.rms.core.ui.theme.EWalletSpacing

@Composable
fun CreateAccountScreen(
    onNavigateUp: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var cpfCnpj by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    androidx.compose.material3.Scaffold(
        topBar = {
            com.example.rms.core.ui.components.EWalletTopBar(
                title = "Create Account",
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
                imageVector = Icons.Default.PersonAdd,
                contentDescription = "Create Account",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.height(64.dp)
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.major))
            
            // Title
            Text(
                text = "Create your account",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Fill in the fields below",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.section))
            
            // Name field
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                placeholder = "Full name"
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            // CPF/CNPJ field
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                value = cpfCnpj,
                onValueChange = { cpfCnpj = it },
                placeholder = "CPF/CNPJ"
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            // Email field
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            // Phone field
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                value = phone,
                onValueChange = { phone = it },
                placeholder = "Phone",
                keyboardType = KeyboardType.Phone
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            // Password field
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                label = "Password"
            )
            
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            
            // Confirm password field
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm password",
                errorMessage = if (confirmPassword.isNotEmpty() && password != confirmPassword) "Passwords do not match" else null
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
            
            // Create button
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Create account",
                onClick = {
                    isLoading = true
                    errorMessage = null
                    
                    if (name.isEmpty() || cpfCnpj.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Please fill in all required fields"
                        isLoading = false
                    } else if (password != confirmPassword) {
                        errorMessage = "Passwords do not match"
                        isLoading = false
                    } else {
                        // Simulate successful creation
                        isLoading = false
                        onNavigateUp()
                    }
                },
                isLoading = isLoading
            )
        }
    }
}