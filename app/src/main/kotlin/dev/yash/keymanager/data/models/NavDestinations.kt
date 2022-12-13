package dev.yash.keymanager.data.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestinations(val title: String, val icon: ImageVector? = null, val route: String) {
    object AuthScreen : NavDestinations("Authentication", null, "authentication")
    object HomeScreen : NavDestinations("Home", null, "home")
    object SshScreen : NavDestinations("SSH", Icons.Filled.Key, "ssh")
    object GpgScreen : NavDestinations("GPG", Icons.Filled.Lock, "gpg")
}
