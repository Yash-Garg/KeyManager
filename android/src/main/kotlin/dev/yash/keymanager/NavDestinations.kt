package dev.yash.keymanager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestinations(val title: String, val icon: ImageVector? = null, val route: String) {
    data object AuthScreen : NavDestinations("Authentication", null, "authentication")

    data object HomeScreen : NavDestinations("Home", null, "home")

    data object SshScreen : NavDestinations("SSH", Icons.Filled.Key, "ssh")

    data object GpgScreen : NavDestinations("GPG", Icons.Filled.Lock, "gpg")

    data object KeyDetailsScreen : NavDestinations("KeyDetails", null, "key_details/{keyId}")
}
