package com.example.rms.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rms.data.remote.AuthRepository
import com.example.rms.data.remote.RetrofitClient
import com.example.rms.presentation.auth.CreateAccountScreen
import com.example.rms.presentation.auth.LoginScreen
import com.example.rms.presentation.auth.RecoveryPasswordScreen
import com.example.rms.presentation.home.HomeScreen
import com.example.rms.presentation.receivable.ReceivableDetailScreen
import com.example.rms.presentation.receipt.ReceiptScreen
import com.example.rms.presentation.scan.ScanScreen
import com.example.rms.presentation.sendmoney.SendMoneyScreen
import com.example.rms.presentation.statistics.StatisticsScreen

sealed interface AppRoute {
    data object Login : AppRoute
    data object CreateAccount : AppRoute
    data object RecoveryPassword : AppRoute
    data object Home : AppRoute
    data object Scan : AppRoute
    data object Statistics : AppRoute
    data object SendMoney : AppRoute
    data object Receipt : AppRoute
    data class ReceivableDetail(val id: String) : AppRoute
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(false) }
    
    val authRepository = remember { AuthRepository(RetrofitClient.apiService) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        isAuthenticated = com.example.rms.data.remote.AuthManager.token != null
    }

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) AppRoute.Home else AppRoute.Login
    ) {
        composable<AppRoute.Login> {
            LoginScreen(
                onLoginSuccess = {
                    isAuthenticated = true
                    navController.navigate(AppRoute.Home) {
                        popUpTo(AppRoute.Login) { inclusive = true }
                    }
                },
                onLoginFailure = { errorMessage ->
                    // Show error to user
                },
                onNavigateToCreateAccount = { navController.navigate(AppRoute.CreateAccount) },
                onNavigateToRecovery = { navController.navigate(AppRoute.RecoveryPassword) },
                authRepository = authRepository
            )
        }
        
        composable<AppRoute.CreateAccount> {
            CreateAccountScreen(
                onNavigateUp = { navController.popBackStack() }
            )
        }
        
        composable<AppRoute.RecoveryPassword> {
            RecoveryPasswordScreen(
                onNavigateUp = { navController.popBackStack() }
            )
        }
        
        composable<AppRoute.Home> {
            HomeScreen(
                onNavigateToScan = { navController.navigate(AppRoute.Scan) },
                onNavigateToStatistics = { navController.navigate(AppRoute.Statistics) },
                onNavigateToSendMoney = { navController.navigate(AppRoute.SendMoney) },
                onReceivableClick = { id -> navController.navigate(AppRoute.ReceivableDetail(id)) },
                onLogout = {
                    authRepository.logout()
                    isAuthenticated = false
                    navController.navigate(AppRoute.Login) {
                        popUpTo(AppRoute.Home) { inclusive = true }
                    }
                }
            )
        }
        
        composable<AppRoute.Scan> {
            ScanScreen(onNavigateUp = { navController.popBackStack() })
        }
        
        composable<AppRoute.Statistics> {
            StatisticsScreen(onNavigateUp = { navController.popBackStack() })
        }
        
        composable<AppRoute.SendMoney> {
            SendMoneyScreen(
                onNavigateUp = { navController.popBackStack() },
                onPaymentSuccess = { navController.navigate(AppRoute.Receipt) }
            )
        }
        
        composable<AppRoute.Receipt> {
            ReceiptScreen(
                onNavigateUp = { navController.popBackStack() },
                onDone = { navController.navigate(AppRoute.Home) { popUpTo(AppRoute.Home) { inclusive = false } } }
            )
        }
        
        composable<AppRoute.ReceivableDetail> { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            ReceivableDetailScreen(
                receivableId = id,
                onNavigateUp = { navController.popBackStack() }
            )
        }
    }
}