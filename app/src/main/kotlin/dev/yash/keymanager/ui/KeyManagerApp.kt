package dev.yash.keymanager.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import dev.yash.keymanager.ui.auth.AuthScreen
import dev.yash.keymanager.ui.theme.KeyManagerTheme

@Composable
fun KeyManagerApp() {
    val navController = rememberNavController()

    KeyManagerTheme { AuthScreen() }
}
