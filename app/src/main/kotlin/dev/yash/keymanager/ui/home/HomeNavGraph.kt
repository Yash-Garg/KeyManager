package dev.yash.keymanager.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.NavDestinations
import dev.yash.keymanager.data.models.SshKey
import dev.yash.keymanager.ui.common.GpgKeyListScreen
import dev.yash.keymanager.ui.common.SshKeyListScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    sshKeys: LazyPagingItems<SshKey>,
    gpgKeys: LazyPagingItems<GpgKey>,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = NavDestinations.SshScreen.route) {
        composable(NavDestinations.SshScreen.route) {
            SshKeyListScreen(lazyPagingItems = sshKeys, modifier = modifier)
        }
        composable(NavDestinations.GpgScreen.route) {
            GpgKeyListScreen(lazyPagingItems = gpgKeys, modifier = modifier)
        }
    }
}
