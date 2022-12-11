package dev.yash.keymanager.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.yash.keymanager.R
import dev.yash.keymanager.data.models.GpgKey
import dev.yash.keymanager.data.models.SshKey
import dev.yash.keymanager.ui.ssh.SshKeyListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UNCHECKED_CAST")
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val sshKeys = viewModel.sshKeys.collectAsLazyPagingItems() as LazyPagingItems<SshKey>
    val gpgKeys = viewModel.gpgKeys.collectAsLazyPagingItems() as LazyPagingItems<GpgKey>
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("SSH KEYS", "GPG KEYS")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.app_name), fontWeight = FontWeight.Bold)
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(Icons.Filled.Lock, null) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/}) { Icon(Icons.Filled.Add, null) }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        SshKeyListScreen(lazyPagingItems = sshKeys, modifier = Modifier.padding(it))
    }
}
