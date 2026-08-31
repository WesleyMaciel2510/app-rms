package com.example.rms.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
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
import com.example.rms.data.remote.AuthRepository
import com.example.rms.data.remote.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onLoginFailure: (String) -> Unit,
    onNavigateToCreateAccount: () -> Unit,
    onNavigateToRecovery: () -> Unit,
    authRepository: AuthRepository
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = EWalletSpacing.standard),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Icon(
            imageVector = Icons.Default.Wallet,
            contentDescription = "E-Wallet Logo",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.height(80.dp)
        )
        
        Spacer(modifier = Modifier.height(EWalletSpacing.major))
        
        // Welcome text
        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Access your account",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(EWalletSpacing.section))
        
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
        
        // Password field
        PasswordField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = "Password",
            errorMessage = errorMessage
        )
        
        Spacer(modifier = Modifier.height(EWalletSpacing.compact))
        
        // Forgot password link
        Text(
            text = "Forgot password?",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = EWalletSpacing.standard)
        )
        
        // Error message
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
        }
        
        // Sign in button
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign In",
            onClick = {
                isLoading = true
                errorMessage = null
                
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Email or password is incorrect"
                    isLoading = false
                } else {
                    // Call real backend
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = authRepository.login(email, password)
                        kotlinx.coroutines.withContext(Dispatchers.Main) {
                            isLoading = false
                            when {
                                result.isSuccess -> onLoginSuccess()
                                result.isFailure -> {
                                    errorMessage = result.exceptionOrNull()?.message ?: "Login failed"
                                    onLoginFailure(errorMessage!!)
                                }
                            }
                        }
                    }
                }
            },
            isLoading = isLoading
        )
        
        Spacer(modifier = Modifier.height(EWalletSpacing.section))
        
        // Create account link
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(EWalletSpacing.micro))
            Button(
                onClick = onNavigateToCreateAccount,
                shape = MaterialTheme.shapes.medium,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Create account",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        
        Spacer(modifier = Modifier.height(EWalletSpacing.major))
        
        // Recovery password link
        Button(
            onClick = onNavigateToRecovery,
            shape = MaterialTheme.shapes.medium,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Recover password",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}