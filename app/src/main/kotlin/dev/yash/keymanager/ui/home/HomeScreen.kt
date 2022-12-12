package dev.yash.keymanager.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.yash.keymanager.R
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.NavDestinations
import dev.yash.keymanager.data.models.SshKey

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNCHECKED_CAST")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val sshKeys = viewModel.sshKeys.collectAsLazyPagingItems() as LazyPagingItems<SshKey>
    val gpgKeys = viewModel.gpgKeys.collectAsLazyPagingItems() as LazyPagingItems<GpgKey>
    val openAddDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                actions = {
                    IconButton(onClick = { /*TODO*/}) { Icon(Icons.TwoTone.ExitToApp, null) }
                }
            )
        },
        bottomBar = { HomeBottomBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { openAddDialog.value = true }) {
                Icon(Icons.Filled.Add, null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        HomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(it),
            sshKeys = sshKeys,
            gpgKeys = gpgKeys
        )
    }
}

@Composable
fun HomeBottomBar(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }
    val screens = listOf(NavDestinations.SshScreen, NavDestinations.GpgScreen)

    NavigationBar {
        screens.forEachIndexed { index, item ->
            NavigationBarItem(
                label = { Text(item.title) },
                selected = selectedTab == index,
                onClick = {
                    selectedTab = index
                    navController.navigate(item.route)
                },
                icon = { Icon(requireNotNull(item.icon), null) }
            )
        }
    }
}
