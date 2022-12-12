package dev.yash.keymanager.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.yash.keymanager.data.models.NavDestinations
import dev.yash.keymanager.ui.auth.AuthScreen
import dev.yash.keymanager.ui.auth.AuthViewModel
import dev.yash.keymanager.ui.home.HomeScreen
import dev.yash.keymanager.ui.home.HomeViewModel
import dev.yash.keymanager.ui.theme.KeyManagerTheme

@Composable
fun KeyManagerApp() {
    val navController = rememberNavController()

    KeyManagerTheme {
        NavHost(
            navController = navController,
            startDestination = NavDestinations.AuthScreen.route
        ) {
            composable(NavDestinations.AuthScreen.route) {
                val authViewModel = hiltViewModel<AuthViewModel>()
                AuthScreen(
                    viewModel = authViewModel,
                    onAuthNavigate = {
                        navController.navigate(NavDestinations.HomeScreen.route) { popUpTo(0) }
                    }
                )
            }
            composable(NavDestinations.HomeScreen.route) {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreen(viewModel = homeViewModel)
            }
        }
    }
}
