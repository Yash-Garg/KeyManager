package dev.yash.keymanager.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.ExitToApp
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.yash.keymanager.R
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.GpgModel
import dev.yash.keymanager.data.models.KeyEvent
import dev.yash.keymanager.data.models.KeyType
import dev.yash.keymanager.data.models.NavDestinations
import dev.yash.keymanager.data.models.SshKey
import dev.yash.keymanager.data.models.SshModel
import dev.yash.keymanager.ui.common.KeyDialogWithTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNCHECKED_CAST")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    onLogoutNavigate: () -> Unit,
    onKeyClickNavigate: (Long) -> Unit
) {
    val context = LocalContext.current

    val sshKeys = viewModel.sshKeys.collectAsLazyPagingItems() as LazyPagingItems<SshKey>
    val sshSigningKeys =
        viewModel.sshSigningKeys.collectAsLazyPagingItems() as LazyPagingItems<SshKey>
    val gpgKeys = viewModel.gpgKeys.collectAsLazyPagingItems() as LazyPagingItems<GpgKey>

    val openLogoutDialog = remember { mutableStateOf(false) }
    val openAddDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.status.collectLatest {
            when (it.event) {
                KeyEvent.ADDED -> {
                    when (it.type) {
                        KeyType.SSH,
                        KeyType.SSH_SIGNING -> {
                            sshKeys.refresh()
                            sshSigningKeys.refresh()
                        }
                        KeyType.GPG -> gpgKeys.refresh()
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                KeyEvent.FAILED -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

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
                    IconButton(onClick = { openLogoutDialog.value = true }) {
                        Icon(Icons.TwoTone.ExitToApp, null)
                    }
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
            sshSigningKeys = sshSigningKeys,
            onKeyClickNavigate = { key -> onKeyClickNavigate(key) },
            gpgKeys = gpgKeys
        )

        if (openLogoutDialog.value) {
            AlertDialog(
                onDismissRequest = { openLogoutDialog.value = false },
                icon = { Icon(Icons.TwoTone.Warning, null, modifier = Modifier.size(35.dp)) },
                title = {
                    Text(
                        text = "Are you sure you want to logout?",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                text = { Text(text = "This will delete the stored access token.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            runBlocking { viewModel.logout() }
                            onLogoutNavigate()
                            openLogoutDialog.value = false
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openLogoutDialog.value = false }) { Text("Dismiss") }
                }
            )
        }

        if (openAddDialog.value) {
            KeyDialogWithTextField(
                title = "Add new key",
                onDismissRequest = { openAddDialog.value = false },
                onConfirm = { title, key, type ->
                    when (type) {
                        KeyType.SSH -> viewModel.createKey(SshModel(title, key))
                        KeyType.SSH_SIGNING -> viewModel.createSigningKey(SshModel(title, key))
                        KeyType.GPG -> viewModel.createKey(GpgModel(title, key))
                    }
                    openAddDialog.value = false
                }
            )
        }
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
                    navController.navigate(item.route) { popUpTo(0) }
                },
                icon = { Icon(requireNotNull(item.icon), null) }
            )
        }
    }
}
