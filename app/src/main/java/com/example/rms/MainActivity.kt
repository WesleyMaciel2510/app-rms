package com.example.rms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.rms.core.navigation.AppNavigation
import com.example.rms.core.ui.theme.EWalletTheme
import com.example.rms.data.local.SessionManager

class MainActivity : ComponentActivity() {
    private val sessionManager by lazy { SessionManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EWalletTheme {
                AppNavigation(sessionManager = sessionManager)
            }
        }
    }
}
