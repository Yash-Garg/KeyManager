package dev.yash.keymanager.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.ExitToApp
import androidx.compose.material.icons.twotone.Lock
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.yash.keymanager.R
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.SshKey
import dev.yash.keymanager.ui.common.GpgKeyListScreen
import dev.yash.keymanager.ui.common.SshKeyListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNCHECKED_CAST")
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val sshKeys = viewModel.sshKeys.collectAsLazyPagingItems() as LazyPagingItems<SshKey>
    val gpgKeys = viewModel.gpgKeys.collectAsLazyPagingItems() as LazyPagingItems<GpgKey>
    var selectedItem by remember { mutableStateOf(0) }
    val openAddDialog = remember { mutableStateOf(false) }
    val items = listOf("SSH KEYS", "GPG KEYS")

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
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(Icons.TwoTone.Lock, null) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openAddDialog.value = true }) {
                Icon(Icons.Filled.Add, null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        when (selectedItem) {
            0 -> SshKeyListScreen(lazyPagingItems = sshKeys, modifier = Modifier.padding(it))
            1 -> GpgKeyListScreen(lazyPagingItems = gpgKeys, modifier = Modifier.padding(it))
        }

        if (openAddDialog.value) {
            AlertDialog(
                onDismissRequest = { openAddDialog.value = false },
                title = { Text(text = "Add new key", fontWeight = FontWeight.SemiBold) },
                text = { Text(text = "Dialog for adding a new SSH / GPG key") },
                confirmButton = {
                    TextButton(onClick = { openAddDialog.value = false }) { Text("Confirm") }
                },
                dismissButton = {
                    TextButton(onClick = { openAddDialog.value = false }) { Text("Dismiss") }
                }
            )
        }
    }
}
