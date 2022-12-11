package dev.yash.keymanager.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.yash.keymanager.ui.auth.AuthScreen
import dev.yash.keymanager.ui.auth.AuthViewModel
import dev.yash.keymanager.ui.home.HomeScreen
import dev.yash.keymanager.ui.home.HomeViewModel
import dev.yash.keymanager.ui.theme.KeyManagerTheme

@Composable
fun KeyManagerApp() {
    val navController = rememberNavController()

    KeyManagerTheme {
        NavHost(navController = navController, startDestination = "authentication") {
            composable("authentication") {
                val authViewModel = hiltViewModel<AuthViewModel>()
                AuthScreen(
                    viewModel = authViewModel,
                    onAuthNavigate = { navController.navigate("home") { popUpTo(0) } }
                )
            }
            composable("home") {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(viewModel = homeViewModel)
            }
        }
    }
}
