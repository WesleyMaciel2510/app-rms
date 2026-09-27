package com.example.rms.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.rms.core.ui.components.PasswordField
import com.example.rms.core.ui.components.PrimaryButton
import com.example.rms.core.ui.components.SearchField
import com.example.rms.core.ui.theme.EWalletSpacing

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToCreateAccount: () -> Unit,
    onNavigateToRecovery: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = EWalletSpacing.section),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(modifier = Modifier.height(EWalletSpacing.hero))

            // Brand header: logo + wordmark + tagline
            Icon(
                imageVector = Icons.Default.Wallet,
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(EWalletSpacing.tight))
            Text(
                text = "E-Wallet",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(EWalletSpacing.micro))
            Text(
                text = "Suas finanças, simplificadas.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(EWalletSpacing.hero))

            // Welcome copy
            Text(
                text = "Bem-vindo de volta",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(EWalletSpacing.micro))
            Text(
                text = "Acesse sua conta para continuar.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(EWalletSpacing.section))

            // Email field
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = "Email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                enabled = !uiState.isLoading,
            )
            if (uiState.emailError != null) {
                Text(
                    text = uiState.emailError!!,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = EWalletSpacing.tight, top = 2.dp),
                )
            }

            Spacer(modifier = Modifier.height(EWalletSpacing.compact))

            // Password field (show/hide via existing PasswordField component)
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = "Senha",
                errorMessage = uiState.passwordError,
                enabled = !uiState.isLoading,
            )

            // Forgot password link
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = onNavigateToRecovery) {
                    Text(
                        text = "Esqueci minha senha",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            Spacer(modifier = Modifier.height(EWalletSpacing.standard))

            // Login button
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Entrar",
                onClick = { viewModel.onLoginClick() },
                isLoading = uiState.isLoading,
            )

            Spacer(modifier = Modifier.height(EWalletSpacing.section))

            // Create account link
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Não tem uma conta?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                TextButton(onClick = onNavigateToCreateAccount) {
                    Text(
                        text = "Criar conta",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Spacer(modifier = Modifier.height(EWalletSpacing.major))
        }
    }
}
