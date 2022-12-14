package dev.yash.keymanager.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeyManagerApp() {
    val navController = rememberNavController()

    KeyManagerTheme {
        Scaffold {
            NavHost(
                modifier = Modifier.padding(it),
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
                    HomeScreen(
                        viewModel = homeViewModel,
                        onLogoutNavigate = {
                            navController.navigate(NavDestinations.AuthScreen.route) { popUpTo(0) }
                        }
                    )
                }
            }
        }
    }
}
