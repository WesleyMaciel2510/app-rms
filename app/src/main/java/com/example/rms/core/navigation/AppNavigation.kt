package com.example.rms.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.rms.data.local.SessionManager
import com.example.rms.data.remote.AuthRepository
import com.example.rms.data.remote.RetrofitClient
import com.example.rms.data.repository.FakeLoginRepository
import com.example.rms.presentation.auth.CreateAccountScreen
import com.example.rms.presentation.auth.LoginScreen
import com.example.rms.presentation.auth.LoginViewModel
import com.example.rms.presentation.auth.RecoveryPasswordScreen
import com.example.rms.presentation.home.HomeScreen
import com.example.rms.presentation.receivable.ReceivableDetailScreen
import com.example.rms.presentation.receipt.ReceiptScreen
import com.example.rms.presentation.scan.ScanScreen
import com.example.rms.presentation.sendmoney.SendMoneyScreen
import com.example.rms.presentation.statistics.StatisticsScreen
import com.example.rms.ui.screens.MainScreen

// ─── Route definitions ───────────────────────────────────────────────────────

sealed interface AuthRoute {
    data object Login : AuthRoute
    data object ForgotPassword : AuthRoute
    data object CreateAccount : AuthRoute
}

sealed interface AppRoute {
    data object Home : AppRoute
    data object Scan : AppRoute
    data object Statistics : AppRoute
    data object SendMoney : AppRoute
    data object Receipt : AppRoute
    data class ReceivableDetail(val id: String) : AppRoute
}

private const val AUTH_GRAPH = "auth_graph"
private const val MAIN_GRAPH = "main_graph"

// ─── Root nav composable ─────────────────────────────────────────────────────

@Composable
fun AppNavigation(sessionManager: SessionManager) {
    // Collect session state; null = still loading from DataStore
    val isAuthenticated by sessionManager.isAuthenticated
        .collectAsStateWithLifecycle(initialValue = null)

    when (isAuthenticated) {
        null -> {
            // DataStore hasn't emitted yet — show a neutral loading state
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {
            AuthAwareNavHost(
                sessionManager = sessionManager,
                startInMainGraph = isAuthenticated == true,
            )
        }
    }
}

// ─── NavHost with two nested graphs ──────────────────────────────────────────

@Composable
private fun AuthAwareNavHost(
    sessionManager: SessionManager,
    startInMainGraph: Boolean,
) {
    val navController = rememberNavController()
    val startDestination = if (startInMainGraph) MAIN_GRAPH else AUTH_GRAPH

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {

        // ── Unauthenticated graph ─────────────────────────────────────────────
        navigation(
            route = AUTH_GRAPH,
            startDestination = "login",
        ) {
            composable("login") {
                val loginRepository = FakeLoginRepository()
                val loginViewModel: LoginViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : ViewModel> create(modelClass: Class<T>): T =
                            LoginViewModel(
                                repository = loginRepository,
                                sessionManager = sessionManager,
                            ) as T
                    }
                )
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        navController.navigate(MAIN_GRAPH) {
                            popUpTo(AUTH_GRAPH) { inclusive = true }
                        }
                    },
                    onNavigateToCreateAccount = { navController.navigate("create_account") },
                    onNavigateToRecovery = { navController.navigate("forgot_password") },
                )
            }

            composable("forgot_password") {
                RecoveryPasswordScreen(onNavigateUp = { navController.popBackStack() })
            }

            composable("create_account") {
                CreateAccountScreen(onNavigateUp = { navController.popBackStack() })
            }
        }

        // ── Authenticated graph ───────────────────────────────────────────────
        navigation(
            route = MAIN_GRAPH,
            startDestination = "main_tabs",
        ) {
            composable("main_tabs") {
                MainScreen(
                    onLogout = {
                        navController.navigate(AUTH_GRAPH) {
                            popUpTo(MAIN_GRAPH) { inclusive = true }
                        }
                    }
                )
            }

            composable("scan") {
                ScanScreen(onNavigateUp = { navController.popBackStack() })
            }

            composable("statistics") {
                StatisticsScreen(onNavigateUp = { navController.popBackStack() })
            }

            composable("send_money") {
                SendMoneyScreen(
                    onNavigateUp = { navController.popBackStack() },
                    onPaymentSuccess = { navController.navigate("receipt") },
                )
            }

            composable("receipt") {
                ReceiptScreen(
                    onNavigateUp = { navController.popBackStack() },
                    onDone = { navController.navigate("main_tabs") { popUpTo("main_tabs") { inclusive = false } } },
                )
            }

            composable("receivable_detail/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: return@composable
                ReceivableDetailScreen(
                    receivableId = id,
                    onNavigateUp = { navController.popBackStack() },
                )
            }
        }
    }
}
