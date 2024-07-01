package dev.yash.keymanager.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.yash.keymanager.data.models.NavDestinations
import dev.yash.keymanager.ui.auth.AuthScreen
import dev.yash.keymanager.ui.auth.AuthViewModel
import dev.yash.keymanager.ui.home.HomeScreen
import dev.yash.keymanager.ui.home.HomeViewModel
import dev.yash.keymanager.ui.keys.KeyDetailsScreen
import dev.yash.keymanager.ui.theme.KeyManagerTheme

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
                        onKeyClickNavigate = { id -> navController.navigate("key_details/$id") },
                        onLogoutNavigate = {
                            navController.navigate(NavDestinations.AuthScreen.route) { popUpTo(0) }
                        }
                    )
                }

                composable(
                    NavDestinations.KeyDetailsScreen.route,
                    arguments = listOf(navArgument("keyId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val keyId = backStackEntry.arguments?.getLong("keyId")

                    if (keyId != null) KeyDetailsScreen(keyId = keyId)
                }
            }
        }
    }
}
