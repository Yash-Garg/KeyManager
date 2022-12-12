package dev.yash.keymanager.data.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestinations(val title: String, val icon: ImageVector? = null, val route: String) {
    object AuthScreen : NavDestinations("Authentication", null, "authentication")
    object HomeScreen : NavDestinations("Home", null, "home")
    object SshScreen : NavDestinations("SSH KEYS", Icons.TwoTone.Lock, "ssh")
    object GpgScreen : NavDestinations("GPG KEYS", Icons.TwoTone.Lock, "gpg")
    object KeyDetailsScreen : NavDestinations("KEY DETAILS", null, "key_details/{keyID}")
}
